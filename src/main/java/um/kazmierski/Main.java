package um.kazmierski;

import com.diogonunes.jcolor.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import um.kazmierski.model.FullMovieInfo;
import um.kazmierski.model.UserCsvMovies;
import um.kazmierski.model.UserFullMovies;
import um.kazmierski.model.csv.CsvMovieInfo;
import um.kazmierski.model.csv.CsvMovieScore;
import um.kazmierski.model.tmdb.TmdbMovie;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;
import static um.kazmierski.utils.TmdbUtil.*;

public class Main {
    private static final String MOVIE_DATA_PATH = "./data/movie_data.json"; // movies downloaded from TMDB
    private static final String MOVIES_TO_DOWNLOAD_PATH = "./data/movie.csv"; // movies to be downloaded (WIKAMP)
    private static final String TRAIN_DATA_PATH = "./data/train.csv"; // training data
    private static final String TASK_DATA_PATH = "./data/task.csv"; // task data
    private static final String API_KEY_PATH = "./data/tmdb_api_key.txt"; // TMDB API key
    private static final String SUBMISSION_FILE_PATH = "./data/submission.csv"; // result file

    private static final int MIN_SAME_SEEN_MOVIES = 1;
    private static final int K_CLOSEST_USERS = 5;

    public static final ObjectMapper mapper = new ObjectMapper();

    private static Map<Integer, TmdbMovie> movieCsvToTmdbMapping = null;

    public static void main(String[] args) throws IOException {
        System.out.println("Time: " + LocalDateTime.now());

        String apiKey = readTmdbToken(new File(API_KEY_PATH));

        File moviesDataFile = new File(MOVIE_DATA_PATH);
        File trainingDataFile = new File(TRAIN_DATA_PATH);
        File taskDataFile = new File(TASK_DATA_PATH);

        File moviesToDownloadFile = new File(MOVIES_TO_DOWNLOAD_PATH);
        List<CsvMovieInfo> csvMovieInfos = readListFromCsv(moviesToDownloadFile, CsvMovieInfo.class);

        if (!moviesDataFile.exists()) {
            System.out.println(colorize("Movie data file not found, downloading...", Attribute.YELLOW_TEXT(),
                                        Attribute.BLACK_BACK()));
            File movieJsonFile = downloadMovieDataToFile(MOVIE_DATA_PATH, csvMovieInfos, apiKey);
            List<TmdbMovie> tmdbMovies = readMovieData(moviesDataFile);
            downloadMovieKeywords(tmdbMovies, movieJsonFile, apiKey);
            downloadCast(tmdbMovies, movieJsonFile, apiKey);
        }

        List<TmdbMovie> tmdbMovies = readMovieData(moviesDataFile);

        // CSV movie index (ID) mapped to a relevant TMDB movie
        movieCsvToTmdbMapping = mapCsvMoviesToTmdbMovies(csvMovieInfos, tmdbMovies);

        List<CsvMovieScore> trainingData = readListFromCsv(trainingDataFile, CsvMovieScore.class);
        List<CsvMovieScore> taskData = readListFromCsv(taskDataFile, CsvMovieScore.class);

        // user ID mapped to seen and unseen user CSV movies
        Map<Integer, UserCsvMovies> allUsersCsvMovies = generateUserIdMoviesMapping(trainingData, taskData);
        final int totalUsers = allUsersCsvMovies.keySet().size();
        final int totalUsers10Percent = totalUsers / 10;

        int userCounter = 1;

        System.out.println("\nExecuting the person similarity based recommendations...\n");

        for (Integer userId : allUsersCsvMovies.keySet()) {
            printProgress(userCounter, totalUsers10Percent, "User", totalUsers);

            final UserCsvMovies userCsvMovies = allUsersCsvMovies.get(userId);
            UserFullMovies currentUserFullMovies = getUserFullMovies(userCsvMovies);

            Set<Integer> similarUsersIDsSorted = findSimilarUsersSorted(userId, currentUserFullMovies, allUsersCsvMovies);

            for (FullMovieInfo movieInfo : currentUserFullMovies.newMovies) {
                movieInfo.csvScore.score = determineMovieScore(movieInfo, similarUsersIDsSorted, allUsersCsvMovies);
            }

            userCounter++;
        }

        saveResultsToFile(taskData);

        System.out.println("\nComplete!");
    }

    /**
     * @param sourceUserId user for whom we are finding recommendations
     * @param sourceUserFullMovies user's movies
     * @param allUsersMovies movies of ALL the users, key is the user ID
     * @return Sorted set of user IDs in ascending order of similarity
     */
    private static Set<Integer> findSimilarUsersSorted(Integer sourceUserId,
                                                        UserFullMovies sourceUserFullMovies,
                                                        Map<Integer, UserCsvMovies> allUsersMovies) {
        // movies of ALL the users, without the current (source) user
        Map<Integer, UserCsvMovies> filteredAllUsersMovies = new HashMap<>(allUsersMovies);
        filteredAllUsersMovies.remove(sourceUserId);

        Map<Integer, Double> userDistances = new HashMap<>();
        for (Integer targetUserId : filteredAllUsersMovies.keySet()) {
            int sameSeenMoviesCounter = 0;
            int distanceSum = 0;
            final UserCsvMovies targetUserCsvMovies = allUsersMovies.get(targetUserId);

            for (FullMovieInfo sourceUserSeenMovie : sourceUserFullMovies.seenMovies) {
                int targetUserMovieScore = getMovieScore(sourceUserSeenMovie, targetUserCsvMovies);

                if (targetUserMovieScore < 0) // target user has not seen the movie
                    continue;

                int movieScoreDistance = Math.abs(sourceUserSeenMovie.csvScore.score - targetUserMovieScore);

                distanceSum += movieScoreDistance;
                sameSeenMoviesCounter++;
            }

            // TODO consider adding a bonus for a high amount of the same movies seen

            if (sameSeenMoviesCounter >= MIN_SAME_SEEN_MOVIES) {
                double userDistance = (double) distanceSum / sameSeenMoviesCounter;
                userDistances.put(targetUserId, userDistance);
            }
        }

        final LinkedHashMap<Integer, Double> userDistancesSorted = userDistances.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return userDistancesSorted.keySet();
    }

    private static int getMovieScore(FullMovieInfo sourceUserSeenMovie, UserCsvMovies targetUserCsvMovies) {
        return targetUserCsvMovies.seenMovies.stream()
                .filter(seenCsvMovieScore -> seenCsvMovieScore.movieCsvIndex == sourceUserSeenMovie.csvScore.movieCsvIndex)
                .findFirst()
                .map(csvMovieScore -> csvMovieScore.score)
                .orElse(-1);
    }

    /**
     * For each CSV movie score assigns a TMDB movie and combines this information into a single
     * object.
     */
    private static UserFullMovies getUserFullMovies(UserCsvMovies userCsvMovies) {
        UserFullMovies userFullMovies = new UserFullMovies();

        for (CsvMovieScore seenCsvMovieScore : userCsvMovies.seenMovies) {
            FullMovieInfo fullMovieInfo = new FullMovieInfo(getTmdbMovie(seenCsvMovieScore), seenCsvMovieScore);
            userFullMovies.seenMovies.add(fullMovieInfo);
        }

        for (CsvMovieScore newCsvMovieScore : userCsvMovies.newMovies) {
            FullMovieInfo fullMovieInfo = new FullMovieInfo(getTmdbMovie(newCsvMovieScore), newCsvMovieScore);
            userFullMovies.newMovies.add(fullMovieInfo);
        }

        return userFullMovies;
    }


    private static void saveResultsToFile(List<CsvMovieScore> taskData) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SUBMISSION_FILE_PATH));
        for (CsvMovieScore taskCsvMovieScore : taskData) {
            writer.write(taskCsvMovieScore.index + ";"
                             + taskCsvMovieScore.userId + ";"
                             + taskCsvMovieScore.movieCsvIndex + ";"
                             + taskCsvMovieScore.score + "\n");
        }
        writer.close();
    }

    private static void printProgress(int counter,
                                      int totalSize10Percent,
                                      @SuppressWarnings("SameParameterValue") String entityNamePrefix,
                                      int totalSize) {
        if (counter % totalSize10Percent == 0) {
            System.out.println(entityNamePrefix + " " + counter + "/" + totalSize
                                       + " (" + counter *10 / totalSize10Percent + "%)");
        }
    }

    private static int determineMovieScore(FullMovieInfo fullMovieInfo,
                                           Set<Integer> similarUsersFullMoviesSorted,
                                           Map<Integer, UserCsvMovies> allUsersCsvMovies) {
        int sum = 0;
        int counter = 0;
        for (Integer similarUserId : similarUsersFullMoviesSorted) {
            if (counter == K_CLOSEST_USERS)
                break;

            final int movieScore = getMovieScore(fullMovieInfo, allUsersCsvMovies.get(similarUserId));
            if (movieScore < 0) // similar user has not seen the movie
                continue;

            sum += movieScore;
            counter++;
        }

        double avg = (double) sum / counter;

        return (int) Math.round(avg);
    }

    private static TmdbMovie getTmdbMovie(CsvMovieScore csvMovie) {
        return movieCsvToTmdbMapping.get(csvMovie.movieCsvIndex);
    }

    private static Map<Integer, UserCsvMovies> generateUserIdMoviesMapping(List<CsvMovieScore> trainingData,
                                                                           List<CsvMovieScore> taskData) {
        Map<Integer, UserCsvMovies> userMovies = new HashMap<>();

        for (CsvMovieScore trainingMovie : trainingData) {
            if (!userMovies.containsKey(trainingMovie.userId))
                userMovies.put(trainingMovie.userId, new UserCsvMovies());

            userMovies.get(trainingMovie.userId).seenMovies.add(trainingMovie);
        }

        for (CsvMovieScore taskMovie : taskData) {
            if (!userMovies.containsKey(taskMovie.userId)) {
                System.out.println(colorize("### NEW USER IN THE TASK DATASET: " + taskMovie.userId,
                                            Attribute.RED_TEXT(), Attribute.BLACK_BACK()));
                userMovies.put(taskMovie.userId, new UserCsvMovies());
            }

            userMovies.get(taskMovie.userId).newMovies.add(taskMovie);
        }

        return userMovies;
    }

    private static Map<Integer, TmdbMovie> mapCsvMoviesToTmdbMovies(List<CsvMovieInfo> csvMovieInfos, List<TmdbMovie> tmdbMovies) {
        Map<Integer, TmdbMovie> movieMap = new HashMap<>();

        for (CsvMovieInfo csvMovieInfo : csvMovieInfos) {
            TmdbMovie tmdbMovie = tmdbMovies.stream()
                    .filter(m -> m.getId().equals(csvMovieInfo.tmdbId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("NO MOVIE WITH ID " + csvMovieInfo.tmdbId));

            movieMap.put(csvMovieInfo.indexCsvId, tmdbMovie);
        }

        return movieMap;
    }

    private static <T> List<T> readListFromCsv(File csv, Class<T> clazz) throws IOException {
        ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(clazz);

        Reader reader = Files.newBufferedReader(csv.toPath());
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withMappingStrategy(mappingStrategy)
                .withType(clazz)
                .withSeparator(';')
                .withIgnoreEmptyLine(true)
                .build();

        List<T> resultList = csvToBean.parse();
        reader.close();

        return resultList;
    }

    /**
     * Read full movie data downloaded from TMDB. The data is stored in .json.
     */
    private static List<TmdbMovie> readMovieData(File moviesDataFile) throws IOException {
        return Arrays.asList(mapper.readValue(moviesDataFile, TmdbMovie[].class));
    }

}
