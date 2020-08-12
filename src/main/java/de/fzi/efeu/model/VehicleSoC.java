package de.fzi.efeu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.fzi.efeu.model.VehicleSoCBoxPosition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VehicleSoC
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-12T13:24:01.196099800+02:00[Europe/Berlin]")

public class VehicleSoC   {
  @JsonProperty("boxId")
  private Integer boxId;

  /**
   * Gets or Sets boxState
   */
  public enum BoxStateEnum {
    IDLE("idle"),
    
    INUSE("inUse"),
    
    UNCLEAN("unclean"),
    
    DISRUPTION("disruption");

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

  @JsonProperty("boxPosition")
  private VehicleSoCBoxPosition boxPosition;

  /**
   * Gets or Sets boxLoad
   */
  public enum BoxLoadEnum {
    EMPTY("empty"),
    
    LOADED("loaded");

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

  @JsonProperty("boxChangeTimestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime boxChangeTimestamp;

  @JsonProperty("vehicleId")
  private Integer vehicleId;

  @JsonProperty("mountId")
  private Integer mountId;

  public VehicleSoC boxId(Integer boxId) {
    this.boxId = boxId;
    return this;
  }

  /**
   * Get boxId
   * @return boxId
  */
  @ApiModelProperty(value = "")


  public Integer getBoxId() {
    return boxId;
  }

  public void setBoxId(Integer boxId) {
    this.boxId = boxId;
  }

  public VehicleSoC boxState(BoxStateEnum boxState) {
    this.boxState = boxState;
    return this;
  }

  /**
   * Get boxState
   * @return boxState
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public BoxStateEnum getBoxState() {
    return boxState;
  }

  public void setBoxState(BoxStateEnum boxState) {
    this.boxState = boxState;
  }

  public VehicleSoC boxPosition(VehicleSoCBoxPosition boxPosition) {
    this.boxPosition = boxPosition;
    return this;
  }

  /**
   * Get boxPosition
   * @return boxPosition
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public VehicleSoCBoxPosition getBoxPosition() {
    return boxPosition;
  }

  public void setBoxPosition(VehicleSoCBoxPosition boxPosition) {
    this.boxPosition = boxPosition;
  }

  public VehicleSoC boxLoad(BoxLoadEnum boxLoad) {
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

  public VehicleSoC boxChangeTimestamp(OffsetDateTime boxChangeTimestamp) {
    this.boxChangeTimestamp = boxChangeTimestamp;
    return this;
  }

  /**
   * Get boxChangeTimestamp
   * @return boxChangeTimestamp
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getBoxChangeTimestamp() {
    return boxChangeTimestamp;
  }

  public void setBoxChangeTimestamp(OffsetDateTime boxChangeTimestamp) {
    this.boxChangeTimestamp = boxChangeTimestamp;
  }

  public VehicleSoC vehicleId(Integer vehicleId) {
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

  public VehicleSoC mountId(Integer mountId) {
    this.mountId = mountId;
    return this;
  }

  /**
   * Get mountId
   * @return mountId
  */
  @ApiModelProperty(value = "")


  public Integer getMountId() {
    return mountId;
  }

  public void setMountId(Integer mountId) {
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
    VehicleSoC vehicleSoC = (VehicleSoC) o;
    return Objects.equals(this.boxId, vehicleSoC.boxId) &&
        Objects.equals(this.boxState, vehicleSoC.boxState) &&
        Objects.equals(this.boxPosition, vehicleSoC.boxPosition) &&
        Objects.equals(this.boxLoad, vehicleSoC.boxLoad) &&
        Objects.equals(this.boxChangeTimestamp, vehicleSoC.boxChangeTimestamp) &&
        Objects.equals(this.vehicleId, vehicleSoC.vehicleId) &&
        Objects.equals(this.mountId, vehicleSoC.mountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boxId, boxState, boxPosition, boxLoad, boxChangeTimestamp, vehicleId, mountId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleSoC {\n");
    
    sb.append("    boxId: ").append(toIndentedString(boxId)).append("\n");
    sb.append("    boxState: ").append(toIndentedString(boxState)).append("\n");
    sb.append("    boxPosition: ").append(toIndentedString(boxPosition)).append("\n");
    sb.append("    boxLoad: ").append(toIndentedString(boxLoad)).append("\n");
    sb.append("    boxChangeTimestamp: ").append(toIndentedString(boxChangeTimestamp)).append("\n");
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

