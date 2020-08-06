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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-06T17:41:28.964+02:00[Europe/Berlin]")

public class ChargingOrder   {
  @JsonProperty("start")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime start;

  @JsonProperty("end")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime end;

  @JsonProperty("vehicle")
  private String vehicle;

  @JsonProperty("charging station")
  private String chargingStation;

  public ChargingOrder start(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  /**
   * Get start
   * @return start
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getStart() {
    return start;
  }

  public void setStart(OffsetDateTime start) {
    this.start = start;
  }

  public ChargingOrder end(OffsetDateTime end) {
    this.end = end;
    return this;
  }

  /**
   * Get end
   * @return end
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getEnd() {
    return end;
  }

  public void setEnd(OffsetDateTime end) {
    this.end = end;
  }

  public ChargingOrder vehicle(String vehicle) {
    this.vehicle = vehicle;
    return this;
  }

  /**
   * Get vehicle
   * @return vehicle
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVehicle() {
    return vehicle;
  }

  public void setVehicle(String vehicle) {
    this.vehicle = vehicle;
  }

  public ChargingOrder chargingStation(String chargingStation) {
    this.chargingStation = chargingStation;
    return this;
  }

  /**
   * Get chargingStation
   * @return chargingStation
  */
  @ApiModelProperty(value = "")


  public String getChargingStation() {
    return chargingStation;
  }

  public void setChargingStation(String chargingStation) {
    this.chargingStation = chargingStation;
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
    return Objects.equals(this.start, chargingOrder.start) &&
        Objects.equals(this.end, chargingOrder.end) &&
        Objects.equals(this.vehicle, chargingOrder.vehicle) &&
        Objects.equals(this.chargingStation, chargingOrder.chargingStation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, vehicle, chargingStation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChargingOrder {\n");
    
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
    sb.append("    vehicle: ").append(toIndentedString(vehicle)).append("\n");
    sb.append("    chargingStation: ").append(toIndentedString(chargingStation)).append("\n");
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

