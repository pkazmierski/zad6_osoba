package um.kazmierski.model;

import um.kazmierski.model.csv.CsvMovieScore;
import um.kazmierski.model.tmdb.TmdbMovie;

public class FullMovieInfo {
    public TmdbMovie tmdb;
    public CsvMovieScore csvScore;

    public FullMovieInfo(TmdbMovie tmdb, CsvMovieScore csvScore) {
        this.tmdb = tmdb;
        this.csvScore = csvScore;
    }
}
