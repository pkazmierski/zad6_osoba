
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
    "id",
    "logo_path",
    "name",
    "origin_country"
})
@Generated("jsonschema2pojo")
public class ProductionCompany {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("logo_path")
    private String logoPath;
    @JsonProperty("name")
    private String name;
    @JsonProperty("origin_country")
    private String originCountry;
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

    public ProductionCompany withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("logo_path")
    public String getLogoPath() {
        return logoPath;
    }

    @JsonProperty("logo_path")
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public ProductionCompany withLogoPath(String logoPath) {
        this.logoPath = logoPath;
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

    public ProductionCompany withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("origin_country")
    public String getOriginCountry() {
        return originCountry;
    }

    @JsonProperty("origin_country")
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public ProductionCompany withOriginCountry(String originCountry) {
        this.originCountry = originCountry;
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

    public ProductionCompany withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "ProductionCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originCountry='" + originCountry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionCompany that = (ProductionCompany) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(ProductionCompany.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
//        sb.append("id");
//        sb.append('=');
//        sb.append(((this.id == null)?"<null>":this.id));
//        sb.append(',');
//        sb.append("logoPath");
//        sb.append('=');
//        sb.append(((this.logoPath == null)?"<null>":this.logoPath));
//        sb.append(',');
//        sb.append("name");
//        sb.append('=');
//        sb.append(((this.name == null)?"<null>":this.name));
//        sb.append(',');
//        sb.append("originCountry");
//        sb.append('=');
//        sb.append(((this.originCountry == null)?"<null>":this.originCountry));
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
//
//    @Override
//    public int hashCode() {
//        int result = 1;
//        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
//        result = ((result* 31)+((this.originCountry == null)? 0 :this.originCountry.hashCode()));
//        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
//        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
//        result = ((result* 31)+((this.logoPath == null)? 0 :this.logoPath.hashCode()));
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof ProductionCompany) == false) {
//            return false;
//        }
//        ProductionCompany rhs = ((ProductionCompany) other);
//        return ((((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.originCountry == rhs.originCountry)||((this.originCountry!= null)&&this.originCountry.equals(rhs.originCountry))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.logoPath == rhs.logoPath)||((this.logoPath!= null)&&this.logoPath.equals(rhs.logoPath))));
//    }

}
