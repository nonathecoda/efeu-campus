package de.fzi.efeu.db.dao;

import org.springframework.data.repository.CrudRepository;

import de.fzi.efeu.db.model.BoxStatus;

public interface BoxStatusRepository extends CrudRepository<BoxStatus, Long> {
    BoxStatus findByBoxId(Long boxId);
}
