package um.kazmierski.model;

import um.kazmierski.model.tmdb.TmdbMovie;

import java.util.function.Function;

public class Criterion {
    public Function<TmdbMovie, Boolean> function;
    public String description;

    public Criterion(Function<TmdbMovie, Boolean> function, String description) {
        this.function = function;
        this.description = description;
    }
}
