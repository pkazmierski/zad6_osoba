package um.kazmierski;

import com.diogonunes.jcolor.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import um.kazmierski.model.*;
import um.kazmierski.model.csv.CsvMovieInfo;
import um.kazmierski.model.csv.CsvMovieScore;
import um.kazmierski.model.tmdb.Genre;
import um.kazmierski.model.tmdb.Keyword;
import um.kazmierski.model.tmdb.TmdbMovie;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
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


    // TASK 5 CONFIG
    private static final int CLASSES_COUNT = 6;
    private static final int MAX_DEPTH = 2;
    private static final int NO_SCORE = -999;
    private static final int NUM_OF_THRESHOLDS = 3;
    private static final int DEPTH_START_ZERO = 0;
    private static final int TOP_CAST_LIMIT = 5;

    // TASK 6 CONFIG
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

    private static Set<Integer> findSimilarUsersSorted(Integer sourceUserId, // user for whom we are finding recommendations
                                                        UserFullMovies sourceUserFullMovies, // user's movies
                                                        Map<Integer, UserCsvMovies> allUsersMovies) { // movies of ALL the users, key is the user ID
        Map<Integer, UserCsvMovies> filteredAllUsersMovies = new HashMap<>(allUsersMovies); // movies of ALL the users, without the current (source) user
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
//        for (CsvMovieScore targetSeenCsvMovieScore : targetUserCsvMovies.seenMovies) {
//            if (targetSeenCsvMovieScore.index == sourceUserSeenMovie.csvScore.index) {
//                return targetSeenCsvMovieScore.score;
//            }
//        }
//
//        return -1;
        return targetUserCsvMovies.seenMovies.stream()
                .filter(seenCsvMovieScore -> seenCsvMovieScore.movieCsvIndex == sourceUserSeenMovie.csvScore.movieCsvIndex)
                .findFirst()
                .map(csvMovieScore -> csvMovieScore.score)
                .orElse(-1);
    }

    public static void printTree(Node root, int userId) {
        @SuppressWarnings("PointlessArithmeticExpression")
        final int linesCount = MAX_DEPTH + ((MAX_DEPTH - 1) * 2);

        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        for (int i = 0; i < linesCount; i++) {
            lines.add(new ArrayList<>());
        }

        saveLines(root, lines);

        System.out.println("\n## Tree for user ID " + userId);
        for (ArrayList<String> line : lines) {
            System.out.println(String.join("\t", line));
        }
    }

    public static void saveLines(Node tree, ArrayList<ArrayList<String>> lines) {
        if (tree.criterion != null) {
            lines.get(tree.depth - 1).add(tree.criterion.description);
            saveLines(tree.left, lines);
            saveLines(tree.right, lines);
        } else {
            lines.get(tree.depth - 1).add(String.valueOf(tree.scoreLabel));
        }
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

    private static Node induceDecisionTree(UserFullMovies userFullMovies) {
        List<Criterion> criteria = generateCriteria(userFullMovies);
        return generateNode(criteria, userFullMovies.seenMovies, DEPTH_START_ZERO);
    }

    private static Node generateNode(List<Criterion> criteria, List<FullMovieInfo> moviesToSplit, int previousDepth) {
        int currentDepth = previousDepth + 1;

        if (moviesToSplit.size() == 1)
            return Node.makeLeaf(moviesToSplit, currentDepth);

        Criterion bestCriterion = null;
        double bestQuality = Double.NEGATIVE_INFINITY;
        double bestGiniIndexLeft = -1.0; // impurity
        double bestGiniIndexRight = -1.0; // impurity
        List<FullMovieInfo> bestLeftMovies = new ArrayList<>(); // matches, true
        List<FullMovieInfo> bestRightMovies = new ArrayList<>(); // no match, false

        for (Criterion criterion : criteria) {
            List<FullMovieInfo> leftMovies = new ArrayList<>();
            List<FullMovieInfo> rightMovies = new ArrayList<>();

            for (FullMovieInfo fullMovie : moviesToSplit) {
                if (criterion.function.apply(fullMovie.tmdb)) { // true = left
                    leftMovies.add(fullMovie);
                } else { // false = right
                    rightMovies.add(fullMovie);
                }
            }

            double giniIndexMoviesToSplit = calculateGiniIndex(moviesToSplit); // Q^R
            double giniIndexLeft = calculateGiniIndex(leftMovies); // Q^L
            double giniIndexRight = calculateGiniIndex(rightMovies); // Q^P
            double leftWeight = (double) leftMovies.size() / moviesToSplit.size();
            double rightWeight = (double) rightMovies.size() / moviesToSplit.size();

            // Q(c), information gain, Gini gain
            double quality = giniIndexMoviesToSplit - (leftWeight * giniIndexLeft + rightWeight * giniIndexRight);

            if (quality > bestQuality) {
                bestQuality = quality;
                bestCriterion = criterion;
                bestLeftMovies = leftMovies;
                bestRightMovies = rightMovies;
                bestGiniIndexLeft = giniIndexLeft;
                bestGiniIndexRight = giniIndexRight;
            }
        }

        Node leftNode;
        Node rightNode;

        if (bestLeftMovies.size() == 0) {
            return Node.makeLeaf(bestRightMovies, currentDepth);
        }
        if (bestRightMovies.size() == 0) {
            return Node.makeLeaf(bestLeftMovies, currentDepth);
        }

        if (currentDepth + 1 < MAX_DEPTH) { // next node is below the threshold
            if (bestGiniIndexLeft > 0.1) { // high impurity
                leftNode = generateNode(criteria, bestLeftMovies, currentDepth);
            } else {
                leftNode = Node.makeLeaf(bestLeftMovies, currentDepth + 1);
            }

            if (bestGiniIndexRight > 0.1) { // high impurity
                rightNode = generateNode(criteria, bestRightMovies, currentDepth);
            } else {
                rightNode = Node.makeLeaf(bestRightMovies, currentDepth + 1);
            }
        } else { // next node will be max depth
            leftNode = Node.makeLeaf(bestLeftMovies, currentDepth + 1);
            rightNode = Node.makeLeaf(bestRightMovies, currentDepth + 1);
        }

        return new Node(leftNode, rightNode, bestCriterion, moviesToSplit, NO_SCORE, currentDepth);
    }

    public static int getMajorityScoreLabel(List<FullMovieInfo> moviesToSplit) {
        return moviesToSplit.stream()
                // map movie to score
                .map(fullMovieInfo -> fullMovieInfo.csvScore.score)
                // summarize scores
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                // fetch the max entry
                .entrySet().stream().max(Map.Entry.comparingByValue())
                // map to score
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalAccessError::new);
    }

    private static double calculateGiniIndex(List<FullMovieInfo> movies) {
        if (movies.size() == 0)
            return 1.0;

        double sum = 0.0;

        List<List<FullMovieInfo>> classes = new ArrayList<>();
        for (int l = 0; l < CLASSES_COUNT; l++) {
            classes.add(new ArrayList<>());
        }

        for (FullMovieInfo movieInfo : movies) {
            int score = movieInfo.csvScore.score;
            classes.get(score).add(movieInfo);
        }


        for (int l = 0; l < CLASSES_COUNT; l++) {
            double pxl = (double) classes.get(l).size() / movies.size(); // p^x_l
            sum += Math.pow(pxl, 2);
        }

        return 1 - sum;
    }

    private static List<Criterion> generateCriteria(UserFullMovies userFullMovies) {
        List<Criterion> criteria = new ArrayList<>();

        // generate criteria for list values
        criteria.addAll(generateAllListCriteria(userFullMovies));
        // generate criteria for continuous values (3 thresholds values)
        criteria.addAll(generateAllContinuousValuesCriteria(userFullMovies));

        return criteria;
    }

    private static Collection<? extends Criterion> generateAllContinuousValuesCriteria(UserFullMovies userFullMovies) {
        ArrayList<Criterion> criteria = new ArrayList<>();

        criteria.addAll(generateSingleContinuousValueCriteriaDouble(
                userFullMovies,
                "popularity below",
                TmdbMovie::getPopularity));

        criteria.addAll(generateSingleContinuousValueCriteriaDouble(
                userFullMovies,
                "vote average below",
                TmdbMovie::getVoteAverage));

        criteria.addAll(generateSingleContinuousValueCriteriaLong(
                userFullMovies,
                "vote count below",
                tmdbMovie -> Long.valueOf(tmdbMovie.getVoteCount())));

        criteria.addAll(generateSingleContinuousValueCriteriaLong(
                userFullMovies,
                "budget below",
                tmdbMovie -> Long.valueOf(tmdbMovie.getBudget())));

        criteria.addAll(generateSingleContinuousValueCriteriaLong(
                userFullMovies,
                "release date before Epoch day",
                tmdbMovie -> getEpochDayFromString(tmdbMovie.getReleaseDate())));

        return criteria;
    }

    private static Collection<? extends Criterion> generateSingleContinuousValueCriteriaDouble(
            UserFullMovies userFullMovies, String descriptionStart, Function<TmdbMovie, Double> comparisonFunction) {
        final ArrayList<Criterion> criteria = new ArrayList<>();

        double min = comparisonFunction.apply(
                userFullMovies.seenMovies
                        .stream()
                        .min(Comparator.comparing(fullMovieInfo -> comparisonFunction.apply(fullMovieInfo.tmdb)))
                        .orElseThrow(NoSuchElementException::new).tmdb);

        double max = comparisonFunction.apply(
                userFullMovies.seenMovies
                        .stream()
                        .max(Comparator.comparing(fullMovieInfo -> comparisonFunction.apply(fullMovieInfo.tmdb)))
                        .orElseThrow(NoSuchElementException::new).tmdb);

        double interval = (max - min) / (double) (NUM_OF_THRESHOLDS + 1);
        ArrayList<Double> thresholds = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_THRESHOLDS; i++) {
            thresholds.add(min + (interval * i));
        }

        thresholds.forEach(thresholdValue -> criteria.add(new Criterion(
                tmdbMovie -> comparisonFunction.apply(tmdbMovie) < thresholdValue,
                descriptionStart + " " + thresholdValue))
        );

        return criteria;
    }

    private static Collection<? extends Criterion> generateSingleContinuousValueCriteriaLong(
            UserFullMovies userFullMovies,
            @SuppressWarnings("SameParameterValue") String descriptionStart,
            Function<TmdbMovie, Long> comparisonFunction) {
        final ArrayList<Criterion> criteria = new ArrayList<>();

        long min = comparisonFunction.apply(
                userFullMovies.seenMovies
                        .stream()
                        .min(Comparator.comparing(fullMovieInfo -> comparisonFunction.apply(fullMovieInfo.tmdb)))
                        .orElseThrow(NoSuchElementException::new).tmdb);

        long max = comparisonFunction.apply(
                userFullMovies.seenMovies
                        .stream()
                        .max(Comparator.comparing(fullMovieInfo -> comparisonFunction.apply(fullMovieInfo.tmdb)))
                        .orElseThrow(NoSuchElementException::new).tmdb);

        long interval = (max - min) / (NUM_OF_THRESHOLDS + 1);
        ArrayList<Long> thresholds = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_THRESHOLDS; i++) {
            thresholds.add(min + (interval * i));
        }

        thresholds.forEach(thresholdValue -> criteria.add(new Criterion(
                tmdbMovie -> comparisonFunction.apply(tmdbMovie) < thresholdValue,
                descriptionStart + " " + thresholdValue))
        );

        return criteria;
    }

    public static long getEpochDayFromString(String dateString) {
        LocalDate localDate = LocalDate.parse(
                dateString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        return localDate.toEpochDay();
    }

    private static Collection<? extends Criterion> generateAllListCriteria(UserFullMovies userFullMovies) {
        final Map<String, Integer> genreCount = new HashMap<>();

        final Map<String, Integer> keywordCount = new HashMap<>();
        
        final Map<String, Integer> castCount = new HashMap<>();

        final Map<String, Integer> directorCount = new HashMap<>();

//        final Map<String, Integer> spokenLanguageCount = new HashMap<>();

        for (FullMovieInfo movie : userFullMovies.seenMovies) {
            List<Genre> movieGenres = movie.tmdb.getGenres();

            movieGenres.forEach(genre -> {
                String genreName = genre.getName();
                if (!genreCount.containsKey(genreName))
                    genreCount.put(genreName, 1);
                else {
                    final Integer integer = genreCount.get(genreName);
                    genreCount.put(genreName, integer + 1);
                }
            });

            final List<String> keywordsForMovie = movie.tmdb.getKeywords().keywords.stream()
                    .map(Keyword::getName).collect(Collectors.toList());

            // keyword count
            keywordsForMovie.forEach(keyword -> {
                if (!keywordCount.containsKey(keyword))
                    keywordCount.put(keyword, 1);
                else {
                    final Integer integer = keywordCount.get(keyword);
                    keywordCount.put(keyword, integer + 1);
                }
            });

            final List<String> castForMovie = movie.tmdb.getTopCastAsList(TOP_CAST_LIMIT);

            castForMovie.forEach(cast -> {
                if (!castCount.containsKey(cast))
                    castCount.put(cast, 1);
                else {
                    final Integer integer = castCount.get(cast);
                    castCount.put(cast, integer + 1);
                }
            });

            final List<String> directorsForMovie = movie.tmdb.getDirectorsAsList();

            directorsForMovie.forEach(director -> {
                if (!directorCount.containsKey(director))
                    directorCount.put(director, 1);
                else {
                    final Integer integer = directorCount.get(director);
                    directorCount.put(director, integer + 1);
                }
            });
        }

        castCount.entrySet().removeIf(e -> e.getValue() == 1);
        directorCount.entrySet().removeIf(e -> e.getValue() == 1);

        final LinkedHashSet<String> allKeywords = new LinkedHashSet<>(keywordCount.keySet());
        final LinkedHashSet<String> allGenres = new LinkedHashSet<>(genreCount.keySet());
        final LinkedHashSet<String> allCast = new LinkedHashSet<>(castCount.keySet());
        final LinkedHashSet<String> allDirectors = new LinkedHashSet<>(directorCount.keySet());

        ArrayList<Criterion> criteria = new ArrayList<>();

        criteria.addAll(generateSingleListCriteria(allGenres,
                                                   "has genre",
                                                   TmdbMovie::getGenresAsList));
        criteria.addAll(generateSingleListCriteria(allKeywords,
                                                   "has keyword",
                                                   TmdbMovie::getKeywordsAsList));

        criteria.addAll(generateSingleListCriteria(allCast,
                                                   "has cast",
                                                   TmdbMovie::getCastAsList));

        criteria.addAll(generateSingleListCriteria(allDirectors,
                                                   "has director",
                                                   TmdbMovie::getDirectorsAsList));

        return criteria;
    }

    private static Collection<? extends Criterion> generateSingleListCriteria(
            LinkedHashSet<String> stringSet,
            String descriptionStart,
            Function<TmdbMovie, List<String>> listFunction) {
        final ArrayList<Criterion> criteria = new ArrayList<>();

        stringSet.forEach(str -> criteria.add(new Criterion(
                tmdbMovie -> listFunction.apply(tmdbMovie).contains(str),
                descriptionStart + " " + str))
        );

        return criteria;
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
