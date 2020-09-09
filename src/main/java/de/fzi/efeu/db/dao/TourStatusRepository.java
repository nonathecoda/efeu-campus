package de.fzi.efeu.db.dao;

import de.fzi.efeu.efeuportal.model.TourStatus;
import org.springframework.data.repository.CrudRepository;

public interface TourStatusRepository extends CrudRepository<TourStatus, Long> {
    TourStatus findTourStatusByTourId(final String tourId);
    void deleteTourStatusByTourId(final String tourId);
}
