package de.fzi.efeu.db.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class VehicleStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique=true)
    private String vehicleId;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private VehicleState vehicleState;

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @NonNull
    private Double remainingRange;

    private String boxId;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NonNull
    private OffsetDateTime timestamp;

}
