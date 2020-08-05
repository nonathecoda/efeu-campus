package de.fzi.efeu.db.dao;

import org.springframework.data.repository.CrudRepository;

import de.fzi.efeu.db.model.TourStatus;

public interface TourStatusRepository extends CrudRepository<TourStatus, Long> {
    TourStatus findTourStatusByTourId(final String tourId);
    void deleteTourStatusByTourId(final String tourId);
}
