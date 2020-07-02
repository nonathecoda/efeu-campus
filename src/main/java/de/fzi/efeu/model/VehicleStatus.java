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
 * VehicleStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T15:04:38.930184900+02:00[Europe/Berlin]")

public class VehicleStatus   {
  @JsonProperty("id")
  private String id;

  /**
   * Gets or Sets vehicleState
   */
  public enum VehicleStateEnum {
    IDLE("Idle"),
    
    DRIVING("Driving"),
    
    RECHARGING("Recharging"),
    
    LOADING("Loading"),
    
    WAITING("Waiting"),
    
    DISRUPTED("Disrupted");

    private String value;

    VehicleStateEnum(String value) {
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
    public static VehicleStateEnum fromValue(String value) {
      for (VehicleStateEnum b : VehicleStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("vehicleState")
  private VehicleStateEnum vehicleState;

  @JsonProperty("latitude")
  private Float latitude;

  @JsonProperty("longitude")
  private Float longitude;

  @JsonProperty("boxId")
  private String boxId;

  @JsonProperty("remainingRange")
  private Integer remainingRange;

  @JsonProperty("timestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  public VehicleStatus id(String id) {
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

  public VehicleStatus vehicleState(VehicleStateEnum vehicleState) {
    this.vehicleState = vehicleState;
    return this;
  }

  /**
   * Get vehicleState
   * @return vehicleState
  */
  @ApiModelProperty(value = "")


  public VehicleStateEnum getVehicleState() {
    return vehicleState;
  }

  public void setVehicleState(VehicleStateEnum vehicleState) {
    this.vehicleState = vehicleState;
  }

  public VehicleStatus latitude(Float latitude) {
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

  public VehicleStatus longitude(Float longitude) {
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

  public VehicleStatus boxId(String boxId) {
    this.boxId = boxId;
    return this;
  }

  /**
   * Get boxId
   * @return boxId
  */
  @ApiModelProperty(value = "")


  public String getBoxId() {
    return boxId;
  }

  public void setBoxId(String boxId) {
    this.boxId = boxId;
  }

  public VehicleStatus remainingRange(Integer remainingRange) {
    this.remainingRange = remainingRange;
    return this;
  }

  /**
   * Get remainingRange
   * @return remainingRange
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getRemainingRange() {
    return remainingRange;
  }

  public void setRemainingRange(Integer remainingRange) {
    this.remainingRange = remainingRange;
  }

  public VehicleStatus timestamp(OffsetDateTime timestamp) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleStatus vehicleStatus = (VehicleStatus) o;
    return Objects.equals(this.id, vehicleStatus.id) &&
        Objects.equals(this.vehicleState, vehicleStatus.vehicleState) &&
        Objects.equals(this.latitude, vehicleStatus.latitude) &&
        Objects.equals(this.longitude, vehicleStatus.longitude) &&
        Objects.equals(this.boxId, vehicleStatus.boxId) &&
        Objects.equals(this.remainingRange, vehicleStatus.remainingRange) &&
        Objects.equals(this.timestamp, vehicleStatus.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vehicleState, latitude, longitude, boxId, remainingRange, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vehicleState: ").append(toIndentedString(vehicleState)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    boxId: ").append(toIndentedString(boxId)).append("\n");
    sb.append("    remainingRange: ").append(toIndentedString(remainingRange)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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

