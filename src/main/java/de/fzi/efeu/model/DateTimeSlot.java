package de.fzi.efeu.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Getter
@Setter

public class DateTimeSlot {
    private OffsetDateTime chargingStart;
    private OffsetDateTime chargingEnd;
}
