package de.fzi.efeu.db.model;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.fzi.efeu.db.converter.EfCaTourConverter;
import de.fzi.efeu.efeuportal.model.EfCaTour;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class TourStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String tourId;

    @NonNull
    private String vehicleId;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private TourState tourState;

    @Convert(converter = EfCaTourConverter.class)
    @NonNull
    private EfCaTour tour;
}
