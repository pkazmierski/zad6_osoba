package um.kazmierski.model.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import um.kazmierski.ConvertNullToInt;

public class CsvMovieScore {

    @CsvBindByPosition(position = 0)
    public int index;

    @CsvBindByPosition(position = 1)
    public int userId;

    @CsvBindByPosition(position = 2)
    public int movieCsvIndex;

    @CsvBindByPosition(position = 3)
    @PreAssignmentProcessor(processor = ConvertNullToInt.class)
    public int score;

    public CsvMovieScore() {
    }

    public CsvMovieScore(int index, int userId, int movieCsvIndex, int score) {
        this.index = index;
        this.userId = userId;
        this.movieCsvIndex = movieCsvIndex;
        this.score = score;
    }
}
