package de.fzi.efeu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ChargingOrder
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-11T12:48:58.805+02:00[Europe/Berlin]")

public class ChargingOrder   {
  @JsonProperty("connection_time")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime connectionTime;

  @JsonProperty("disconnection_time")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime disconnectionTime;

  @JsonProperty("vehicleId")
  private Integer vehicleId;

  @JsonProperty("charging station Id")
  private Integer chargingStationId;

  @JsonProperty("amount_energy")
  private Float amountEnergy = null;

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

  public ChargingOrder vehicleId(Integer vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

  /**
   * Get vehicleId
   * @return vehicleId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(Integer vehicleId) {
    this.vehicleId = vehicleId;
  }

  public ChargingOrder chargingStationId(Integer chargingStationId) {
    this.chargingStationId = chargingStationId;
    return this;
  }

  /**
   * Get chargingStationId
   * @return chargingStationId
  */
  @ApiModelProperty(value = "")


  public Integer getChargingStationId() {
    return chargingStationId;
  }

  public void setChargingStationId(Integer chargingStationId) {
    this.chargingStationId = chargingStationId;
  }

  public ChargingOrder amountEnergy(Float amountEnergy) {
    this.amountEnergy = amountEnergy;
    return this;
  }

  /**
   * Get amountEnergy
   * @return amountEnergy
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getAmountEnergy() {
    return amountEnergy;
  }

  public void setAmountEnergy(Float amountEnergy) {
    this.amountEnergy = amountEnergy;
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
        Objects.equals(this.chargingStationId, chargingOrder.chargingStationId) &&
        Objects.equals(this.amountEnergy, chargingOrder.amountEnergy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectionTime, disconnectionTime, vehicleId, chargingStationId, amountEnergy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChargingOrder {\n");
    
    sb.append("    connectionTime: ").append(toIndentedString(connectionTime)).append("\n");
    sb.append("    disconnectionTime: ").append(toIndentedString(disconnectionTime)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    chargingStationId: ").append(toIndentedString(chargingStationId)).append("\n");
    sb.append("    amountEnergy: ").append(toIndentedString(amountEnergy)).append("\n");
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

