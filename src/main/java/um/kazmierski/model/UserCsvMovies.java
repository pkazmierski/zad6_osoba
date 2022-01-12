package um.kazmierski.model;

import um.kazmierski.model.csv.CsvMovieScore;

import java.util.ArrayList;
import java.util.List;

public class UserCsvMovies {
    public List<CsvMovieScore> seenMovies;
    public List<CsvMovieScore> newMovies;

    public UserCsvMovies() {
        this.seenMovies = new ArrayList<>();
        this.newMovies = new ArrayList<>();
    }
}
