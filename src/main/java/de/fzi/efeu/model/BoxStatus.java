package de.fzi.efeu.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;

/**
 * BoxStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T15:04:38.930184900+02:00[Europe/Berlin]")

public class BoxStatus   {
  @JsonProperty("id")
  private String id;

  /**
   * Gets or Sets boxState
   */
  public enum BoxStateEnum {
    FREE("Free"),
    
    INUSE("InUse"),
    
    DIRTY("Dirty"),
    
    DISRUPTED("Disrupted");

    private String value;

    BoxStateEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static BoxStateEnum fromValue(String value) {
      for (BoxStateEnum b : BoxStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("boxState")
  private BoxStateEnum boxState;

  @JsonProperty("latitude")
  private Float latitude;

  @JsonProperty("longitude")
  private Float longitude;

  /**
   * Gets or Sets boxLoad
   */
  public enum BoxLoadEnum {
    EMPTY("Empty"),
    
    FULL("Full");

    private String value;

    BoxLoadEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static BoxLoadEnum fromValue(String value) {
      for (BoxLoadEnum b : BoxLoadEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("boxLoad")
  private BoxLoadEnum boxLoad;

  @JsonProperty("timestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  @JsonProperty("vehicleId")
  private String vehicleId;

  @JsonProperty("mountId")
  private String mountId;

  public BoxStatus id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BoxStatus boxState(BoxStateEnum boxState) {
    this.boxState = boxState;
    return this;
  }

  /**
   * Get boxState
   * @return boxState
  */
  @ApiModelProperty(value = "")


  public BoxStateEnum getBoxState() {
    return boxState;
  }

  public void setBoxState(BoxStateEnum boxState) {
    this.boxState = boxState;
  }

  public BoxStatus latitude(Float latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Get latitude
   * @return latitude
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public BoxStatus longitude(Float longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Get longitude
   * @return longitude
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public BoxStatus boxLoad(BoxLoadEnum boxLoad) {
    this.boxLoad = boxLoad;
    return this;
  }

  /**
   * Get boxLoad
   * @return boxLoad
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public BoxLoadEnum getBoxLoad() {
    return boxLoad;
  }

  public void setBoxLoad(BoxLoadEnum boxLoad) {
    this.boxLoad = boxLoad;
  }

  public BoxStatus timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public BoxStatus vehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

  /**
   * Get vehicleId
   * @return vehicleId
  */
  @ApiModelProperty(value = "")


  public String getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public BoxStatus mountId(String mountId) {
    this.mountId = mountId;
    return this;
  }

  /**
   * Get mountId
   * @return mountId
  */
  @ApiModelProperty(value = "")


  public String getMountId() {
    return mountId;
  }

  public void setMountId(String mountId) {
    this.mountId = mountId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoxStatus boxStatus = (BoxStatus) o;
    return Objects.equals(this.id, boxStatus.id) &&
        Objects.equals(this.boxState, boxStatus.boxState) &&
        Objects.equals(this.latitude, boxStatus.latitude) &&
        Objects.equals(this.longitude, boxStatus.longitude) &&
        Objects.equals(this.boxLoad, boxStatus.boxLoad) &&
        Objects.equals(this.timestamp, boxStatus.timestamp) &&
        Objects.equals(this.vehicleId, boxStatus.vehicleId) &&
        Objects.equals(this.mountId, boxStatus.mountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, boxState, latitude, longitude, boxLoad, timestamp, vehicleId, mountId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoxStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    boxState: ").append(toIndentedString(boxState)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    boxLoad: ").append(toIndentedString(boxLoad)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    mountId: ").append(toIndentedString(mountId)).append("\n");
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

