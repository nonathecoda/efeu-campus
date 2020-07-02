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
 * OrderStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T15:04:38.930184900+02:00[Europe/Berlin]")

public class OrderStatus   {
  @JsonProperty("id")
  private String id;

  /**
   * Gets or Sets orderState
   */
  public enum OrderStateEnum {
    NEW("New"),
    
    READYFORPLANNING("ReadyForPlanning"),
    
    PLANNED("Planned"),
    
    INEXECUTION("InExecution"),
    
    FINISHED("Finished"),
    
    ABORTED("Aborted"),
    
    DISRUPTED("Disrupted");

    private String value;

    OrderStateEnum(String value) {
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
    public static OrderStateEnum fromValue(String value) {
      for (OrderStateEnum b : OrderStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("orderState")
  private OrderStateEnum orderState;

  @JsonProperty("timestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  public OrderStatus id(String id) {
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

  public OrderStatus orderState(OrderStateEnum orderState) {
    this.orderState = orderState;
    return this;
  }

  /**
   * Get orderState
   * @return orderState
  */
  @ApiModelProperty(value = "")


  public OrderStateEnum getOrderState() {
    return orderState;
  }

  public void setOrderState(OrderStateEnum orderState) {
    this.orderState = orderState;
  }

  public OrderStatus timestamp(OffsetDateTime timestamp) {
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
    OrderStatus orderStatus = (OrderStatus) o;
    return Objects.equals(this.id, orderStatus.id) &&
        Objects.equals(this.orderState, orderStatus.orderState) &&
        Objects.equals(this.timestamp, orderStatus.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderState, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderState: ").append(toIndentedString(orderState)).append("\n");
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

