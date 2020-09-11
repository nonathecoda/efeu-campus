package de.fzi.efeu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.fzi.efeu.model.StatusStationStationPosition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StatusVehicle
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-11T12:48:58.805+02:00[Europe/Berlin]")

public class StatusVehicle   {
  /**
   * Gets or Sets vehicleState
   */
  public enum VehicleStateEnum {
    IDLE("idle"),
    
    DRIVING("driving"),
    
    CHARGING("charging"),
    
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
  private StatusStationStationPosition vehiclePosition;

  @JsonProperty("vehicleSoC")
  private Float vehicleSoC = null;

  public StatusVehicle vehicleState(VehicleStateEnum vehicleState) {
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

  public StatusVehicle vehiclePosition(StatusStationStationPosition vehiclePosition) {
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

  public StatusStationStationPosition getVehiclePosition() {
    return vehiclePosition;
  }

  public void setVehiclePosition(StatusStationStationPosition vehiclePosition) {
    this.vehiclePosition = vehiclePosition;
  }

  public StatusVehicle vehicleSoC(Float vehicleSoC) {
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
    StatusVehicle statusVehicle = (StatusVehicle) o;
    return Objects.equals(this.vehicleState, statusVehicle.vehicleState) &&
        Objects.equals(this.vehiclePosition, statusVehicle.vehiclePosition) &&
        Objects.equals(this.vehicleSoC, statusVehicle.vehicleSoC);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleState, vehiclePosition, vehicleSoC);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatusVehicle {\n");
    
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

