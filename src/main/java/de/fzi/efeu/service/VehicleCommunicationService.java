package de.fzi.efeu.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.fzi.efeu.efeuportal.model.EfCaTour;

@Service
public class VehicleCommunicationService {

    @Value("${simulation.url}")
    private String simulationUrl;

    public void sendVehicleSchedules(final List<EfCaTour> plannedTours) {
        // ToDo: communication with actual vehicles
        RestTemplate restTemplate = new RestTemplate();
        Map<String, List<EfCaTour>> toursByVehicle = new HashMap<>();
        for (final EfCaTour plannedTour : plannedTours) {
            String vehicleId = plannedTour.getTourHeader().getVehicleExtId1();
            toursByVehicle.putIfAbsent(vehicleId, new ArrayList<>());
            toursByVehicle.get(vehicleId).add(plannedTour);
        }
        toursByVehicle.forEach((vehicleId, tours) -> {
            tours.sort(Comparator.comparing(t -> t.getTourHeader().getStartDateTime()));
            ResponseEntity<String> response = restTemplate.postForEntity(
                    simulationUrl + "/schedules/" + vehicleId, tours, String.class);
        });

    }
}
