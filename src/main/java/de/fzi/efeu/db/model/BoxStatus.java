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
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BoxStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique=true)
    private String boxId;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private BoxState boxState;

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private BoxLoad boxLoad;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NonNull
    private OffsetDateTime timestamp;

    private String vehicleId;
    private String mountId;
}
