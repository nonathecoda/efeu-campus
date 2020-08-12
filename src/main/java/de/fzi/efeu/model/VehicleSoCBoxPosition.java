package de.fzi.efeu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VehicleSoCBoxPosition
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-12T13:24:01.196099800+02:00[Europe/Berlin]")

public class VehicleSoCBoxPosition   {
  @JsonProperty("lat")
  private Float lat;

  @JsonProperty("long")
  private Float _long;

  public VehicleSoCBoxPosition lat(Float lat) {
    this.lat = lat;
    return this;
  }

  /**
   * Get lat
   * @return lat
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getLat() {
    return lat;
  }

  public void setLat(Float lat) {
    this.lat = lat;
  }

  public VehicleSoCBoxPosition _long(Float _long) {
    this._long = _long;
    return this;
  }

  /**
   * Get _long
   * @return _long
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getLong() {
    return _long;
  }

  public void setLong(Float _long) {
    this._long = _long;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleSoCBoxPosition vehicleSoCBoxPosition = (VehicleSoCBoxPosition) o;
    return Objects.equals(this.lat, vehicleSoCBoxPosition.lat) &&
        Objects.equals(this._long, vehicleSoCBoxPosition._long);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, _long);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleSoCBoxPosition {\n");
    
    sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
    sb.append("    _long: ").append(toIndentedString(_long)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
