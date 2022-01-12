package um.kazmierski.model;

import java.util.ArrayList;
import java.util.List;

public class UserFullMovies {
    public List<FullMovieInfo> seenMovies;
    public List<FullMovieInfo> newMovies;

    public UserFullMovies() {
        this.seenMovies = new ArrayList<>();
        this.newMovies = new ArrayList<>();
    }
}
