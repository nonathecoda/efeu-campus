package de.fzi.efeu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;

@Service
public class TimeProvider {
    @Value("${simulation.mode}")
    private boolean simulationMode;

    @Value("${simulation.url}")
    private String simulationUrl;

    public OffsetDateTime now() {
        if (simulationMode) {
            return getTimeFromSimulation();
        }
        return OffsetDateTime.now();
    }

    private OffsetDateTime getTimeFromSimulation() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> simulationTimeResponse = restTemplate.getForEntity(
                simulationUrl + "/time/", String.class);
        String simulationTimeString = simulationTimeResponse.getBody();

        return OffsetDateTime.parse(simulationTimeString);
    }
}
