package de.fzi.efeu.db.dao;

import org.springframework.data.repository.CrudRepository;

import de.fzi.efeu.db.model.MountStatus;

public interface MountStatusRepository extends CrudRepository<MountStatus, Long> {
    MountStatus findByMountId(String mountId);
}
