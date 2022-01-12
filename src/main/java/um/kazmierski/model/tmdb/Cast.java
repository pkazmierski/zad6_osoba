
package um.kazmierski.model.tmdb;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "adult",
    "gender",
    "id",
    "known_for_department",
    "name",
    "original_name",
    "popularity",
    "profile_path",
    "cast_id",
    "character",
    "credit_id",
    "order"
})
@Generated("jsonschema2pojo")
public class Cast {

    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("gender")
    private Integer gender;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    @JsonProperty("name")
    private String name;
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("profile_path")
    private Object profilePath;
    @JsonProperty("cast_id")
    private Integer castId;
    @JsonProperty("character")
    private String character;
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("order")
    private Integer order;
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

    @JsonProperty("gender")
    public Integer getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("known_for_department")
    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    @JsonProperty("known_for_department")
    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("original_name")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("original_name")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("profile_path")
    public Object getProfilePath() {
        return profilePath;
    }

    @JsonProperty("profile_path")
    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    @JsonProperty("cast_id")
    public Integer getCastId() {
        return castId;
    }

    @JsonProperty("cast_id")
    public void setCastId(Integer castId) {
        this.castId = castId;
    }

    @JsonProperty("character")
    public String getCharacter() {
        return character;
    }

    @JsonProperty("character")
    public void setCharacter(String character) {
        this.character = character;
    }

    @JsonProperty("credit_id")
    public String getCreditId() {
        return creditId;
    }

    @JsonProperty("credit_id")
    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    @JsonProperty("order")
    public Integer getOrder() {
        return order;
    }

    @JsonProperty("order")
    public void setOrder(Integer order) {
        this.order = order;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Cast{" +
                "adult=" + adult +
                ", gender=" + gender +
                ", id=" + id +
                ", knownForDepartment='" + knownForDepartment + '\'' +
                ", name='" + name + '\'' +
                ", originalName='" + originalName + '\'' +
                ", popularity=" + popularity +
                ", profilePath=" + profilePath +
                ", castId=" + castId +
                ", character='" + character + '\'' +
                ", creditId='" + creditId + '\'' +
                ", order=" + order +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cast cast = (Cast) o;
        return id.equals(cast.id) && name.equals(cast.name);
    }
}
