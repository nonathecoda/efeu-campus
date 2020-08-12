package de.fzi.efeu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * ChargingOrder
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-12T13:24:01.196099800+02:00[Europe/Berlin]")

public class ChargingOrder   {
  @JsonProperty("connection_time")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime connectionTime;

  @JsonProperty("disconnection_time")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime disconnectionTime;

  @JsonProperty("vehicleId")
  private String vehicleId;

  @JsonProperty("charging station Id")
  private String chargingStationId;

  public ChargingOrder connectionTime(OffsetDateTime connectionTime) {
    this.connectionTime = connectionTime;
    return this;
  }

  /**
   * Get connectionTime
   * @return connectionTime
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getConnectionTime() {
    return connectionTime;
  }

  public void setConnectionTime(OffsetDateTime connectionTime) {
    this.connectionTime = connectionTime;
  }

  public ChargingOrder disconnectionTime(OffsetDateTime disconnectionTime) {
    this.disconnectionTime = disconnectionTime;
    return this;
  }

  /**
   * Get disconnectionTime
   * @return disconnectionTime
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getDisconnectionTime() {
    return disconnectionTime;
  }

  public void setDisconnectionTime(OffsetDateTime disconnectionTime) {
    this.disconnectionTime = disconnectionTime;
  }

  public ChargingOrder vehicleId(String vehicleId) {
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

  public ChargingOrder chargingStationId(String chargingStationId) {
    this.chargingStationId = chargingStationId;
    return this;
  }

  /**
   * Get chargingStationId
   * @return chargingStationId
  */
  @ApiModelProperty(value = "")


  public String getChargingStationId() {
    return chargingStationId;
  }

  public void setChargingStationId(String chargingStationId) {
    this.chargingStationId = chargingStationId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChargingOrder chargingOrder = (ChargingOrder) o;
    return Objects.equals(this.connectionTime, chargingOrder.connectionTime) &&
        Objects.equals(this.disconnectionTime, chargingOrder.disconnectionTime) &&
        Objects.equals(this.vehicleId, chargingOrder.vehicleId) &&
        Objects.equals(this.chargingStationId, chargingOrder.chargingStationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectionTime, disconnectionTime, vehicleId, chargingStationId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChargingOrder {\n");
    
    sb.append("    connectionTime: ").append(toIndentedString(connectionTime)).append("\n");
    sb.append("    disconnectionTime: ").append(toIndentedString(disconnectionTime)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    chargingStationId: ").append(toIndentedString(chargingStationId)).append("\n");
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

