package de.fzi.efeu.db.dao;

import org.springframework.data.repository.CrudRepository;

import de.fzi.efeu.db.model.ChargingStationStatus;

public interface ChargingStationStatusRepository extends CrudRepository<ChargingStationStatus, Long> {
    ChargingStationStatus findByChargingStationId(String chargingStationId);
}
