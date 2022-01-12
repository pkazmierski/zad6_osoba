package um.kazmierski.model.csv;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;

public class CsvMovieInfo {
    @CsvBindByPosition(position = 0)
    public int indexCsvId;
    @CsvBindByPosition(position = 1)
    public int tmdbId;
    @CsvBindByPosition(position = 2)
    public String title;

    public CsvMovieInfo() {
    }

    public CsvMovieInfo(int indexCsvId, int tmdbId, String title) {
        this.indexCsvId = indexCsvId;
        this.tmdbId = tmdbId;
        this.title = title;
    }

    @Override
    public String toString() {
        return "CsvMovieInfo{" +
                "indexCsvId=" + indexCsvId +
                ", tmdbId=" + tmdbId +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvMovieInfo csvMovieInfo = (CsvMovieInfo) o;
        return tmdbId == csvMovieInfo.tmdbId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tmdbId);
    }
}
