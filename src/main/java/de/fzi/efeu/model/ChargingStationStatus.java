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
 * ChargingStationStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T15:04:38.930184900+02:00[Europe/Berlin]")

public class ChargingStationStatus   {
  @JsonProperty("id")
  private String id;

  /**
   * Gets or Sets chargingStationState
   */
  public enum ChargingStationStateEnum {
    FREE("Free"),
    
    INUSE("InUse"),
    
    DISRUPTED("Disrupted");

    private String value;

    ChargingStationStateEnum(String value) {
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
    public static ChargingStationStateEnum fromValue(String value) {
      for (ChargingStationStateEnum b : ChargingStationStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("chargingStationState")
  private ChargingStationStateEnum chargingStationState;

  @JsonProperty("vehicleId")
  private String vehicleId;

  @JsonProperty("timestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  public ChargingStationStatus id(String id) {
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

  public ChargingStationStatus chargingStationState(ChargingStationStateEnum chargingStationState) {
    this.chargingStationState = chargingStationState;
    return this;
  }

  /**
   * Get chargingStationState
   * @return chargingStationState
  */
  @ApiModelProperty(value = "")


  public ChargingStationStateEnum getChargingStationState() {
    return chargingStationState;
  }

  public void setChargingStationState(ChargingStationStateEnum chargingStationState) {
    this.chargingStationState = chargingStationState;
  }

  public ChargingStationStatus vehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

  /**
   * Get vehicleId
   * @return vehicleId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public ChargingStationStatus timestamp(OffsetDateTime timestamp) {
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
    ChargingStationStatus chargingStationStatus = (ChargingStationStatus) o;
    return Objects.equals(this.id, chargingStationStatus.id) &&
        Objects.equals(this.chargingStationState, chargingStationStatus.chargingStationState) &&
        Objects.equals(this.vehicleId, chargingStationStatus.vehicleId) &&
        Objects.equals(this.timestamp, chargingStationStatus.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, chargingStationState, vehicleId, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChargingStationStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    chargingStationState: ").append(toIndentedString(chargingStationState)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
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

