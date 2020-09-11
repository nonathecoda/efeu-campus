package de.fzi.efeu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.fzi.efeu.model.StatusStationStationPosition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StatusStation
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-11T12:48:58.805+02:00[Europe/Berlin]")

public class StatusStation   {
  @JsonProperty("stationId")
  private Integer stationId;

  /**
   * Gets or Sets stationState
   */
  public enum StationStateEnum {
    IDLE("idle"),
    
    INUSE("inUse"),
    
    DISRUPTION("disruption");

    private String value;

    StationStateEnum(String value) {
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
    public static StationStateEnum fromValue(String value) {
      for (StationStateEnum b : StationStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("stationState")
  private StationStateEnum stationState;

  @JsonProperty("stationPosition")
  private StatusStationStationPosition stationPosition;

  @JsonProperty("stationChargeTimestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime stationChargeTimestamp;

  public StatusStation stationId(Integer stationId) {
    this.stationId = stationId;
    return this;
  }

  /**
   * Get stationId
   * @return stationId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getStationId() {
    return stationId;
  }

  public void setStationId(Integer stationId) {
    this.stationId = stationId;
  }

  public StatusStation stationState(StationStateEnum stationState) {
    this.stationState = stationState;
    return this;
  }

  /**
   * Get stationState
   * @return stationState
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public StationStateEnum getStationState() {
    return stationState;
  }

  public void setStationState(StationStateEnum stationState) {
    this.stationState = stationState;
  }

  public StatusStation stationPosition(StatusStationStationPosition stationPosition) {
    this.stationPosition = stationPosition;
    return this;
  }

  /**
   * Get stationPosition
   * @return stationPosition
  */
  @ApiModelProperty(value = "")

  @Valid

  public StatusStationStationPosition getStationPosition() {
    return stationPosition;
  }

  public void setStationPosition(StatusStationStationPosition stationPosition) {
    this.stationPosition = stationPosition;
  }

  public StatusStation stationChargeTimestamp(OffsetDateTime stationChargeTimestamp) {
    this.stationChargeTimestamp = stationChargeTimestamp;
    return this;
  }

  /**
   * Get stationChargeTimestamp
   * @return stationChargeTimestamp
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getStationChargeTimestamp() {
    return stationChargeTimestamp;
  }

  public void setStationChargeTimestamp(OffsetDateTime stationChargeTimestamp) {
    this.stationChargeTimestamp = stationChargeTimestamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatusStation statusStation = (StatusStation) o;
    return Objects.equals(this.stationId, statusStation.stationId) &&
        Objects.equals(this.stationState, statusStation.stationState) &&
        Objects.equals(this.stationPosition, statusStation.stationPosition) &&
        Objects.equals(this.stationChargeTimestamp, statusStation.stationChargeTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stationId, stationState, stationPosition, stationChargeTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatusStation {\n");
    
    sb.append("    stationId: ").append(toIndentedString(stationId)).append("\n");
    sb.append("    stationState: ").append(toIndentedString(stationState)).append("\n");
    sb.append("    stationPosition: ").append(toIndentedString(stationPosition)).append("\n");
    sb.append("    stationChargeTimestamp: ").append(toIndentedString(stationChargeTimestamp)).append("\n");
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

