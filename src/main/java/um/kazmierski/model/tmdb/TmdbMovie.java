
package um.kazmierski.model.tmdb;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "adult",
    "backdrop_path",
    "belongs_to_collection",
    "budget",
    "genres",
    "homepage",
    "id",
    "imdb_id",
    "original_language",
    "original_title",
    "overview",
    "popularity",
    "poster_path",
    "production_companies",
    "production_countries",
    "release_date",
    "revenue",
    "runtime",
    "spoken_languages",
    "status",
    "tagline",
    "title",
    "video",
    "vote_average",
    "vote_count",
    "keywords"
})
@Generated("jsonschema2pojo")
public class TmdbMovie {
    @JsonProperty("cast_and_crew")
    public CastAndCrew castAndCrew;

    @JsonProperty("cast_and_crew")
    public CastAndCrew getCastAndCrew() {
        return castAndCrew;
    }

    @JsonProperty("cast_and_crew")
    public void setCastAndCrew(CastAndCrew castAndCrew) {
        this.castAndCrew = castAndCrew;
    }

    @JsonProperty("keywords")
    public Keywords keywords;

    @JsonProperty("keywords")
    public Keywords getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    @JsonIgnore
    public List<String> getTopCastAsList(int limit) {
        return castAndCrew.getCast().stream()
                .sorted(Comparator.comparing(cast -> ((Cast) cast).getPopularity()).reversed())
                .limit(limit)
                .map(Cast::getName)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<String> getDirectorsAsList() {
        return castAndCrew.getCrew().stream()
                .filter(crew -> crew.getJob().equals("Director"))
                .map(Crew::getName)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<String> getCastAsList() {
        return castAndCrew.getCast().stream().map(Cast::getName).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<String> getKeywordsAsList() {
        return keywords.keywords.stream().map(Keyword::getName).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<String> getGenresAsList() {
        return genres.stream().map(Genre::getName).collect(Collectors.toList());
    }

    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("belongs_to_collection")
    private Object belongsToCollection;
    @JsonProperty("budget")
    private Integer budget;
    @JsonProperty("genres")
    private List<Genre> genres = new ArrayList<Genre>();
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();
    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("revenue")
    private Integer revenue;
    @JsonProperty("runtime")
    private Integer runtime;
    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();
    @JsonProperty("status")
    private String status;
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("title")
    private String title;
    @JsonProperty("video")
    private Boolean video;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("adult")
    public Boolean getAdult() {
        return adult;
    }

    @JsonProperty("adult")
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public TmdbMovie withAdult(Boolean adult) {
        this.adult = adult;
        return this;
    }

    @JsonProperty("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }

    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public TmdbMovie withBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    @JsonProperty("belongs_to_collection")
    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    @JsonProperty("belongs_to_collection")
    public void setBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public TmdbMovie withBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
        return this;
    }

    @JsonProperty("budget")
    public Integer getBudget() {
        return budget;
    }

    @JsonProperty("budget")
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public TmdbMovie withBudget(Integer budget) {
        this.budget = budget;
        return this;
    }

    @JsonProperty("genres")
    public List<Genre> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public TmdbMovie withGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    @JsonProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    @JsonProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public TmdbMovie withHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public TmdbMovie withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("imdb_id")
    public String getImdbId() {
        return imdbId;
    }

    @JsonProperty("imdb_id")
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public TmdbMovie withImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    @JsonProperty("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public TmdbMovie withOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    @JsonProperty("original_title")
    public String getOriginalTitle() {
        return originalTitle;
    }

    @JsonProperty("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public TmdbMovie withOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public TmdbMovie withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public TmdbMovie withPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    @JsonProperty("poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public TmdbMovie withPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    @JsonProperty("production_companies")
    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    @JsonProperty("production_companies")
    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public TmdbMovie withProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
        return this;
    }

    @JsonProperty("production_countries")
    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    @JsonProperty("production_countries")
    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public TmdbMovie withProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
        return this;
    }

    @JsonProperty("release_date")
    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public TmdbMovie withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @JsonProperty("revenue")
    public Integer getRevenue() {
        return revenue;
    }

    @JsonProperty("revenue")
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public TmdbMovie withRevenue(Integer revenue) {
        this.revenue = revenue;
        return this;
    }

    @JsonProperty("runtime")
    public Integer getRuntime() {
        return runtime;
    }

    @JsonProperty("runtime")
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public TmdbMovie withRuntime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    @JsonProperty("spoken_languages")
    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    @JsonProperty("spoken_languages")
    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public TmdbMovie withSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public TmdbMovie withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    @JsonProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public TmdbMovie withTagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public TmdbMovie withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("video")
    public Boolean getVideo() {
        return video;
    }

    @JsonProperty("video")
    public void setVideo(Boolean video) {
        this.video = video;
    }

    public TmdbMovie withVideo(Boolean video) {
        this.video = video;
        return this;
    }

    @JsonProperty("vote_average")
    public Double getVoteAverage() {
        return voteAverage;
    }

    @JsonProperty("vote_average")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public TmdbMovie withVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    @JsonProperty("vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public TmdbMovie withVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public TmdbMovie withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TmdbMovie tmdbMovie = (TmdbMovie) o;
        return id.equals(tmdbMovie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(Movie.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
//        sb.append("adult");
//        sb.append('=');
//        sb.append(((this.adult == null)?"<null>":this.adult));
//        sb.append(',');
//        sb.append("backdropPath");
//        sb.append('=');
//        sb.append(((this.backdropPath == null)?"<null>":this.backdropPath));
//        sb.append(',');
//        sb.append("belongsToCollection");
//        sb.append('=');
//        sb.append(((this.belongsToCollection == null)?"<null>":this.belongsToCollection));
//        sb.append(',');
//        sb.append("budget");
//        sb.append('=');
//        sb.append(((this.budget == null)?"<null>":this.budget));
//        sb.append(',');
//        sb.append("genres");
//        sb.append('=');
//        sb.append(((this.genres == null)?"<null>":this.genres));
//        sb.append(',');
//        sb.append("homepage");
//        sb.append('=');
//        sb.append(((this.homepage == null)?"<null>":this.homepage));
//        sb.append(',');
//        sb.append("id");
//        sb.append('=');
//        sb.append(((this.id == null)?"<null>":this.id));
//        sb.append(',');
//        sb.append("imdbId");
//        sb.append('=');
//        sb.append(((this.imdbId == null)?"<null>":this.imdbId));
//        sb.append(',');
//        sb.append("originalLanguage");
//        sb.append('=');
//        sb.append(((this.originalLanguage == null)?"<null>":this.originalLanguage));
//        sb.append(',');
//        sb.append("originalTitle");
//        sb.append('=');
//        sb.append(((this.originalTitle == null)?"<null>":this.originalTitle));
//        sb.append(',');
//        sb.append("overview");
//        sb.append('=');
//        sb.append(((this.overview == null)?"<null>":this.overview));
//        sb.append(',');
//        sb.append("popularity");
//        sb.append('=');
//        sb.append(((this.popularity == null)?"<null>":this.popularity));
//        sb.append(',');
//        sb.append("posterPath");
//        sb.append('=');
//        sb.append(((this.posterPath == null)?"<null>":this.posterPath));
//        sb.append(',');
//        sb.append("productionCompanies");
//        sb.append('=');
//        sb.append(((this.productionCompanies == null)?"<null>":this.productionCompanies));
//        sb.append(',');
//        sb.append("productionCountries");
//        sb.append('=');
//        sb.append(((this.productionCountries == null)?"<null>":this.productionCountries));
//        sb.append(',');
//        sb.append("releaseDate");
//        sb.append('=');
//        sb.append(((this.releaseDate == null)?"<null>":this.releaseDate));
//        sb.append(',');
//        sb.append("revenue");
//        sb.append('=');
//        sb.append(((this.revenue == null)?"<null>":this.revenue));
//        sb.append(',');
//        sb.append("runtime");
//        sb.append('=');
//        sb.append(((this.runtime == null)?"<null>":this.runtime));
//        sb.append(',');
//        sb.append("spokenLanguages");
//        sb.append('=');
//        sb.append(((this.spokenLanguages == null)?"<null>":this.spokenLanguages));
//        sb.append(',');
//        sb.append("status");
//        sb.append('=');
//        sb.append(((this.status == null)?"<null>":this.status));
//        sb.append(',');
//        sb.append("tagline");
//        sb.append('=');
//        sb.append(((this.tagline == null)?"<null>":this.tagline));
//        sb.append(',');
//        sb.append("title");
//        sb.append('=');
//        sb.append(((this.title == null)?"<null>":this.title));
//        sb.append(',');
//        sb.append("video");
//        sb.append('=');
//        sb.append(((this.video == null)?"<null>":this.video));
//        sb.append(',');
//        sb.append("voteAverage");
//        sb.append('=');
//        sb.append(((this.voteAverage == null)?"<null>":this.voteAverage));
//        sb.append(',');
//        sb.append("voteCount");
//        sb.append('=');
//        sb.append(((this.voteCount == null)?"<null>":this.voteCount));
//        sb.append(',');
//        sb.append("additionalProperties");
//        sb.append('=');
//        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
//        sb.append(',');
//        if (sb.charAt((sb.length()- 1)) == ',') {
//            sb.setCharAt((sb.length()- 1), ']');
//        } else {
//            sb.append(']');
//        }
//        return sb.toString();
//    }

//    @Override
//    public int hashCode() {
//        int result = 1;
//        result = ((result* 31)+((this.imdbId == null)? 0 :this.imdbId.hashCode()));
//        result = ((result* 31)+((this.video == null)? 0 :this.video.hashCode()));
//        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
//        result = ((result* 31)+((this.productionCountries == null)? 0 :this.productionCountries.hashCode()));
//        result = ((result* 31)+((this.revenue == null)? 0 :this.revenue.hashCode()));
//        result = ((result* 31)+((this.genres == null)? 0 :this.genres.hashCode()));
//        result = ((result* 31)+((this.popularity == null)? 0 :this.popularity.hashCode()));
//        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
//        result = ((result* 31)+((this.budget == null)? 0 :this.budget.hashCode()));
//        result = ((result* 31)+((this.posterPath == null)? 0 :this.posterPath.hashCode()));
//        result = ((result* 31)+((this.overview == null)? 0 :this.overview.hashCode()));
//        result = ((result* 31)+((this.voteAverage == null)? 0 :this.voteAverage.hashCode()));
//        result = ((result* 31)+((this.releaseDate == null)? 0 :this.releaseDate.hashCode()));
//        result = ((result* 31)+((this.belongsToCollection == null)? 0 :this.belongsToCollection.hashCode()));
//        result = ((result* 31)+((this.runtime == null)? 0 :this.runtime.hashCode()));
//        result = ((result* 31)+((this.originalLanguage == null)? 0 :this.originalLanguage.hashCode()));
//        result = ((result* 31)+((this.originalTitle == null)? 0 :this.originalTitle.hashCode()));
//        result = ((result* 31)+((this.tagline == null)? 0 :this.tagline.hashCode()));
//        result = ((result* 31)+((this.spokenLanguages == null)? 0 :this.spokenLanguages.hashCode()));
//        result = ((result* 31)+((this.backdropPath == null)? 0 :this.backdropPath.hashCode()));
//        result = ((result* 31)+((this.voteCount == null)? 0 :this.voteCount.hashCode()));
//        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
//        result = ((result* 31)+((this.adult == null)? 0 :this.adult.hashCode()));
//        result = ((result* 31)+((this.productionCompanies == null)? 0 :this.productionCompanies.hashCode()));
//        result = ((result* 31)+((this.homepage == null)? 0 :this.homepage.hashCode()));
//        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
//        return result;
//    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof Movie) == false) {
//            return false;
//        }
//        Movie rhs = ((Movie) other);
//        return (((((((((((((((((((((((((((this.imdbId == rhs.imdbId) || ((this.imdbId != null) && this.imdbId.equals(
//                rhs.imdbId))) && ((this.video == rhs.video) || ((this.video != null) && this.video.equals(
//                rhs.video)))) && ((this.title == rhs.title) || ((this.title != null) && this.title.equals(
//                rhs.title)))) && ((this.productionCountries == rhs.productionCountries) || ((this.productionCountries != null) && this.productionCountries.equals(
//                rhs.productionCountries)))) && ((this.revenue == rhs.revenue) || ((this.revenue != null) && this.revenue.equals(
//                rhs.revenue)))) && ((this.genres == rhs.genres) || ((this.genres != null) && this.genres.equals(
//                rhs.genres)))) && ((this.popularity == rhs.popularity) || ((this.popularity != null) && this.popularity.equals(
//                rhs.popularity)))) && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(
//                rhs.id)))) && ((this.budget == rhs.budget) || ((this.budget != null) && this.budget.equals(
//                rhs.budget)))) && ((this.posterPath == rhs.posterPath) || ((this.posterPath != null) && this.posterPath.equals(
//                rhs.posterPath)))) && ((this.overview == rhs.overview) || ((this.overview != null) && this.overview.equals(
//                rhs.overview)))) && ((this.voteAverage == rhs.voteAverage) || ((this.voteAverage != null) && this.voteAverage.equals(
//                rhs.voteAverage)))) && ((this.releaseDate == rhs.releaseDate) || ((this.releaseDate != null) && this.releaseDate.equals(
//                rhs.releaseDate)))) && ((this.belongsToCollection == rhs.belongsToCollection) || ((this.belongsToCollection != null) && this.belongsToCollection.equals(
//                rhs.belongsToCollection)))) && ((this.runtime == rhs.runtime) || ((this.runtime != null) && this.runtime.equals(
//                rhs.runtime)))) && ((this.originalLanguage == rhs.originalLanguage) || ((this.originalLanguage != null) && this.originalLanguage.equals(
//                rhs.originalLanguage)))) && ((this.originalTitle == rhs.originalTitle) || ((this.originalTitle != null) && this.originalTitle.equals(
//                rhs.originalTitle)))) && ((this.tagline == rhs.tagline) || ((this.tagline != null) && this.tagline.equals(
//                rhs.tagline)))) && ((this.spokenLanguages == rhs.spokenLanguages) || ((this.spokenLanguages != null) && this.spokenLanguages.equals(
//                rhs.spokenLanguages)))) && ((this.backdropPath == rhs.backdropPath) || ((this.backdropPath != null) && this.backdropPath.equals(
//                rhs.backdropPath)))) && ((this.voteCount == rhs.voteCount) || ((this.voteCount != null) && this.voteCount.equals(
//                rhs.voteCount)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(
//                rhs.additionalProperties)))) && ((this.adult == rhs.adult) || ((this.adult != null) && this.adult.equals(
//                rhs.adult)))) && ((this.productionCompanies == rhs.productionCompanies) || ((this.productionCompanies != null) && this.productionCompanies.equals(
//                rhs.productionCompanies)))) && ((this.homepage == rhs.homepage) || ((this.homepage != null) && this.homepage.equals(
//                rhs.homepage)))) && ((this.status == rhs.status) || ((this.status != null) && this.status.equals(
//                rhs.status))));
//    }

}
