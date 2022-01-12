
package um.kazmierski.model.tmdb;

import java.util.HashMap;
import java.util.Map;
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
    "credit_id",
    "department",
    "job"
})
@Generated("jsonschema2pojo")
public class Crew {

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
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;
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

    public Crew withAdult(Boolean adult) {
        this.adult = adult;
        return this;
    }

    @JsonProperty("gender")
    public Integer getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Crew withGender(Integer gender) {
        this.gender = gender;
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

    public Crew withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("known_for_department")
    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    @JsonProperty("known_for_department")
    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public Crew withKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Crew withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("original_name")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("original_name")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Crew withOriginalName(String originalName) {
        this.originalName = originalName;
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

    public Crew withPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    @JsonProperty("profile_path")
    public Object getProfilePath() {
        return profilePath;
    }

    @JsonProperty("profile_path")
    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    public Crew withProfilePath(Object profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    @JsonProperty("credit_id")
    public String getCreditId() {
        return creditId;
    }

    @JsonProperty("credit_id")
    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Crew withCreditId(String creditId) {
        this.creditId = creditId;
        return this;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    public Crew withDepartment(String department) {
        this.department = department;
        return this;
    }

    @JsonProperty("job")
    public String getJob() {
        return job;
    }

    @JsonProperty("job")
    public void setJob(String job) {
        this.job = job;
    }

    public Crew withJob(String job) {
        this.job = job;
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

    public Crew withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Crew.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("adult");
        sb.append('=');
        sb.append(((this.adult == null)?"<null>":this.adult));
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(((this.gender == null)?"<null>":this.gender));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("knownForDepartment");
        sb.append('=');
        sb.append(((this.knownForDepartment == null)?"<null>":this.knownForDepartment));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("originalName");
        sb.append('=');
        sb.append(((this.originalName == null)?"<null>":this.originalName));
        sb.append(',');
        sb.append("popularity");
        sb.append('=');
        sb.append(((this.popularity == null)?"<null>":this.popularity));
        sb.append(',');
        sb.append("profilePath");
        sb.append('=');
        sb.append(((this.profilePath == null)?"<null>":this.profilePath));
        sb.append(',');
        sb.append("creditId");
        sb.append('=');
        sb.append(((this.creditId == null)?"<null>":this.creditId));
        sb.append(',');
        sb.append("department");
        sb.append('=');
        sb.append(((this.department == null)?"<null>":this.department));
        sb.append(',');
        sb.append("job");
        sb.append('=');
        sb.append(((this.job == null)?"<null>":this.job));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.gender == null)? 0 :this.gender.hashCode()));
        result = ((result* 31)+((this.knownForDepartment == null)? 0 :this.knownForDepartment.hashCode()));
        result = ((result* 31)+((this.originalName == null)? 0 :this.originalName.hashCode()));
        result = ((result* 31)+((this.creditId == null)? 0 :this.creditId.hashCode()));
        result = ((result* 31)+((this.popularity == null)? 0 :this.popularity.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.profilePath == null)? 0 :this.profilePath.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.adult == null)? 0 :this.adult.hashCode()));
        result = ((result* 31)+((this.department == null)? 0 :this.department.hashCode()));
        result = ((result* 31)+((this.job == null)? 0 :this.job.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Crew) == false) {
            return false;
        }
        Crew rhs = ((Crew) other);
        return (((((((((((((this.gender == rhs.gender)||((this.gender!= null)&&this.gender.equals(rhs.gender)))&&((this.knownForDepartment == rhs.knownForDepartment)||((this.knownForDepartment!= null)&&this.knownForDepartment.equals(rhs.knownForDepartment))))&&((this.originalName == rhs.originalName)||((this.originalName!= null)&&this.originalName.equals(rhs.originalName))))&&((this.creditId == rhs.creditId)||((this.creditId!= null)&&this.creditId.equals(rhs.creditId))))&&((this.popularity == rhs.popularity)||((this.popularity!= null)&&this.popularity.equals(rhs.popularity))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.profilePath == rhs.profilePath)||((this.profilePath!= null)&&this.profilePath.equals(rhs.profilePath))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.adult == rhs.adult)||((this.adult!= null)&&this.adult.equals(rhs.adult))))&&((this.department == rhs.department)||((this.department!= null)&&this.department.equals(rhs.department))))&&((this.job == rhs.job)||((this.job!= null)&&this.job.equals(rhs.job))));
    }

}
