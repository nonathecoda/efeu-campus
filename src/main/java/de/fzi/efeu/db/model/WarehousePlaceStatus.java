package de.fzi.efeu.db.model;

import java.time.LocalDateTime;

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
public class WarehousePlaceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String wareHousePlaceId;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private WarehousePlaceState warehousePlaceState;

    @Column(columnDefinition = "TIMESTAMP")
    @NonNull
    private LocalDateTime timestamp;

    private String efeuPackageId;
}
