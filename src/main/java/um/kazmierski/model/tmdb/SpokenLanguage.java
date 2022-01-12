
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
    "english_name",
    "iso_639_1",
    "name"
})
@Generated("jsonschema2pojo")
public class SpokenLanguage {

    @JsonProperty("english_name")
    private String englishName;
    @JsonProperty("iso_639_1")
    private String iso6391;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("english_name")
    public String getEnglishName() {
        return englishName;
    }

    @JsonProperty("english_name")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public SpokenLanguage withEnglishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    @JsonProperty("iso_639_1")
    public String getIso6391() {
        return iso6391;
    }

    @JsonProperty("iso_639_1")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public SpokenLanguage withIso6391(String iso6391) {
        this.iso6391 = iso6391;
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

    public SpokenLanguage withName(String name) {
        this.name = name;
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

    public SpokenLanguage withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpokenLanguage.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("englishName");
        sb.append('=');
        sb.append(((this.englishName == null)?"<null>":this.englishName));
        sb.append(',');
        sb.append("iso6391");
        sb.append('=');
        sb.append(((this.iso6391 == null)?"<null>":this.iso6391));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.englishName == null)? 0 :this.englishName.hashCode()));
        result = ((result* 31)+((this.iso6391 == null)? 0 :this.iso6391 .hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpokenLanguage) == false) {
            return false;
        }
        SpokenLanguage rhs = ((SpokenLanguage) other);
        return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.englishName == rhs.englishName)||((this.englishName!= null)&&this.englishName.equals(rhs.englishName))))&&((this.iso6391 == rhs.iso6391)||((this.iso6391 != null)&&this.iso6391 .equals(rhs.iso6391))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
