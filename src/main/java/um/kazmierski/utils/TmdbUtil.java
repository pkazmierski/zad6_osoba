package um.kazmierski.utils;

import com.fasterxml.jackson.databind.SequenceWriter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import um.kazmierski.model.csv.CsvMovieInfo;
import um.kazmierski.model.tmdb.CastAndCrew;
import um.kazmierski.model.tmdb.Keywords;
import um.kazmierski.model.tmdb.TmdbMovie;

import java.io.*;
import java.util.List;

import static um.kazmierski.Main.mapper;

public class TmdbUtil {
    private final static String MOVIE_DETAILS_BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static File downloadMovieDataToFile(String targetFilePath, List<CsvMovieInfo> moviesToDownload, String apiKey)
            throws IOException {
        new FileWriter(targetFilePath, false).close(); // clear the file

        File targetFile = new File(targetFilePath);
        FileWriter fileWriter = new FileWriter(targetFile, true);
        SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);

        int waitingToFlushCounter = 1;

        for (CsvMovieInfo csvMovieInfo : moviesToDownload) {
            final TmdbMovie tmdbMovieDetails = getMovieDetails(csvMovieInfo, apiKey);
            seqWriter.write(tmdbMovieDetails);
            if (waitingToFlushCounter % 5 == 0) {
                seqWriter.flush();
                waitingToFlushCounter = 0;
            }
            waitingToFlushCounter++;
        }

        seqWriter.close();
        return targetFile;
    }

    public static void downloadCast(List<TmdbMovie> tmdbMovies, File movieJsonFile, String apiKey)
            throws IOException {
        new FileWriter(movieJsonFile, false).close(); // clear the file

        FileWriter fileWriter = new FileWriter(movieJsonFile, true);
        SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);

        int waitingToFlushCounter = 1;

        for (TmdbMovie tmdbMovie : tmdbMovies) {
            tmdbMovie.castAndCrew = getCast(tmdbMovie, apiKey);
            seqWriter.write(tmdbMovie);
            if (waitingToFlushCounter % 5 == 0) {
                seqWriter.flush();
                waitingToFlushCounter = 0;
            }
            waitingToFlushCounter++;
        }

        seqWriter.close();
    }

    private static CastAndCrew getCast(TmdbMovie tmdbMovie, String apiKey) throws IOException {
        String fullUrl = MOVIE_DETAILS_BASE_URL + tmdbMovie.getId() + "/credits?api_key=" + apiKey;
        CastAndCrew castAndCrew;

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(fullUrl);

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println("Status (cast) for TMDB ID " + tmdbMovie.getId() + ": " + response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                castAndCrew = mapper.readValue(response.getEntity().getContent(), CastAndCrew.class);
                EntityUtils.consume(httpEntity); // finish handling this entity
            }
        }

        return castAndCrew;
    }

    public static void downloadMovieKeywords(List<TmdbMovie> tmdbMovies, File movieJsonFile, String apiKey)
            throws IOException {
        new FileWriter(movieJsonFile, false).close(); // clear the file

        FileWriter fileWriter = new FileWriter(movieJsonFile, true);
        SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);

        int waitingToFlushCounter = 1;

        for (TmdbMovie tmdbMovie : tmdbMovies) {
            tmdbMovie.keywords = getMovieKeywords(tmdbMovie, apiKey);
            seqWriter.write(tmdbMovie);
            if (waitingToFlushCounter % 5 == 0) {
                seqWriter.flush();
                waitingToFlushCounter = 0;
            }
            waitingToFlushCounter++;
        }

        seqWriter.close();
    }

    private static Keywords getMovieKeywords(TmdbMovie tmdbMovie, String apiKey) throws IOException {
        String fullUrl = MOVIE_DETAILS_BASE_URL + tmdbMovie.getId() + "/keywords?api_key=" + apiKey;
        Keywords keywords;

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(fullUrl);

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println("Status for TMDB ID " + tmdbMovie.getId() + ": " + response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                keywords = mapper.readValue(response.getEntity().getContent(), Keywords.class);
                System.out.println("  " + keywords);
                EntityUtils.consume(httpEntity); // finish handling this entity
            }
        }

        return keywords;
    }

    public static TmdbMovie getMovieDetails(CsvMovieInfo csvMovieInfo, String apiKey) throws IOException {
        String fullUrl = MOVIE_DETAILS_BASE_URL + csvMovieInfo.tmdbId + "?api_key=" + apiKey;
        TmdbMovie tmdbMovie;

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(fullUrl);

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println("Status for ID " + csvMovieInfo.tmdbId + ": " + response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                tmdbMovie = mapper.readValue(response.getEntity().getContent(), TmdbMovie.class);

                System.out.println("  "
                                           + "ID: " + tmdbMovie.getId() + "\n  "
                                           + "title: " + tmdbMovie.getTitle() + "\n  "
                                           + "CSV title: " + csvMovieInfo.title
                );

                EntityUtils.consume(httpEntity); // finish handling this entity
            }
        }

        return tmdbMovie;
    }

    public static String readTmdbToken(File tokenFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(tokenFile));
        String token = br.readLine().trim();
        br.close();
        return token;
    }
}
