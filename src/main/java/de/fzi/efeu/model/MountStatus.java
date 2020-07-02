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
 * MountStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T15:04:38.930184900+02:00[Europe/Berlin]")

public class MountStatus   {
  @JsonProperty("id")
  private String id;

  /**
   * Gets or Sets mountState
   */
  public enum MountStateEnum {
    FREE("Free"),
    
    INUSE("InUse"),
    
    DISRUPTED("Disrupted");

    private String value;

    MountStateEnum(String value) {
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
    public static MountStateEnum fromValue(String value) {
      for (MountStateEnum b : MountStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("mountState")
  private MountStateEnum mountState;

  @JsonProperty("boxId")
  private String boxId;

  @JsonProperty("timestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  public MountStatus id(String id) {
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

  public MountStatus mountState(MountStateEnum mountState) {
    this.mountState = mountState;
    return this;
  }

  /**
   * Get mountState
   * @return mountState
  */
  @ApiModelProperty(value = "")


  public MountStateEnum getMountState() {
    return mountState;
  }

  public void setMountState(MountStateEnum mountState) {
    this.mountState = mountState;
  }

  public MountStatus boxId(String boxId) {
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

  public MountStatus timestamp(OffsetDateTime timestamp) {
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
    MountStatus mountStatus = (MountStatus) o;
    return Objects.equals(this.id, mountStatus.id) &&
        Objects.equals(this.mountState, mountStatus.mountState) &&
        Objects.equals(this.boxId, mountStatus.boxId) &&
        Objects.equals(this.timestamp, mountStatus.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, mountState, boxId, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MountStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    mountState: ").append(toIndentedString(mountState)).append("\n");
    sb.append("    boxId: ").append(toIndentedString(boxId)).append("\n");
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

