
package um.kazmierski.model.tmdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "id",
    "cast",
    "crew"
})
@Generated("jsonschema2pojo")
public class CastAndCrew {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("cast")
    private List<Cast> cast = new ArrayList<Cast>();
    @JsonProperty("crew")
    private List<Crew> crew = new ArrayList<Crew>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public CastAndCrew withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("cast")
    public List<Cast> getCast() {
        return cast;
    }

    @JsonProperty("cast")
    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public CastAndCrew withCast(List<Cast> cast) {
        this.cast = cast;
        return this;
    }

    @JsonProperty("crew")
    public List<Crew> getCrew() {
        return crew;
    }

    @JsonProperty("crew")
    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public CastAndCrew withCrew(List<Crew> crew) {
        this.crew = crew;
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

    public CastAndCrew withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                CastAndCrew.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("cast");
        sb.append('=');
        sb.append(((this.cast == null)?"<null>":this.cast));
        sb.append(',');
        sb.append("crew");
        sb.append('=');
        sb.append(((this.crew == null)?"<null>":this.crew));
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
        result = ((result* 31)+((this.cast == null)? 0 :this.cast.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.crew == null)? 0 :this.crew.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CastAndCrew) == false) {
            return false;
        }
        CastAndCrew rhs = ((CastAndCrew) other);
        return (((((this.cast == rhs.cast)||((this.cast!= null)&&this.cast.equals(rhs.cast)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.crew == rhs.crew)||((this.crew!= null)&&this.crew.equals(rhs.crew))));
    }

}
