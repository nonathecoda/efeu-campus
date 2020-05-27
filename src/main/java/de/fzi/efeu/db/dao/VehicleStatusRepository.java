package de.fzi.efeu.db.dao;

import org.springframework.data.repository.CrudRepository;

import de.fzi.efeu.db.model.VehicleStatus;

public interface VehicleStatusRepository extends CrudRepository<VehicleStatus, Long> {
}
