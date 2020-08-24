package de.fzi.efeu.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Vehicle
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-24T14:20:58.073280700+02:00[Europe/Berlin]")

public class Vehicle   {
  @JsonProperty("vehicleId")
  private String vehicleId;

  /**
   * Gets or Sets vehicleState
   */
  public enum VehicleStateEnum {
    IDLE("idle"),
    
    DRIVING("driving"),
    
    RECHARGING("recharging"),
    
    DISRUPTION("disruption");

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

  @JsonProperty("vehiclePosition")
  private VehicleVehiclePosition vehiclePosition;

  @JsonProperty("vehicleSoC")
  private Float vehicleSoC = null;

  public Vehicle vehicleId(String vehicleId) {
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

  public Vehicle vehicleState(VehicleStateEnum vehicleState) {
    this.vehicleState = vehicleState;
    return this;
  }

  /**
   * Get vehicleState
   * @return vehicleState
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public VehicleStateEnum getVehicleState() {
    return vehicleState;
  }

  public void setVehicleState(VehicleStateEnum vehicleState) {
    this.vehicleState = vehicleState;
  }

  public Vehicle vehiclePosition(VehicleVehiclePosition vehiclePosition) {
    this.vehiclePosition = vehiclePosition;
    return this;
  }

  /**
   * Get vehiclePosition
   * @return vehiclePosition
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public VehicleVehiclePosition getVehiclePosition() {
    return vehiclePosition;
  }

  public void setVehiclePosition(VehicleVehiclePosition vehiclePosition) {
    this.vehiclePosition = vehiclePosition;
  }

  public Vehicle vehicleSoC(Float vehicleSoC) {
    this.vehicleSoC = vehicleSoC;
    return this;
  }

  /**
   * Get vehicleSoC
   * @return vehicleSoC
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Float getVehicleSoC() {
    return vehicleSoC;
  }

  public void setVehicleSoC(Float vehicleSoC) {
    this.vehicleSoC = vehicleSoC;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vehicle vehicle = (Vehicle) o;
    return Objects.equals(this.vehicleId, vehicle.vehicleId) &&
        Objects.equals(this.vehicleState, vehicle.vehicleState) &&
        Objects.equals(this.vehiclePosition, vehicle.vehiclePosition) &&
        Objects.equals(this.vehicleSoC, vehicle.vehicleSoC);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, vehicleState, vehiclePosition, vehicleSoC);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vehicle {\n");
    
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    vehicleState: ").append(toIndentedString(vehicleState)).append("\n");
    sb.append("    vehiclePosition: ").append(toIndentedString(vehiclePosition)).append("\n");
    sb.append("    vehicleSoC: ").append(toIndentedString(vehicleSoC)).append("\n");
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

