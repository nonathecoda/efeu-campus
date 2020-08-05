package de.fzi.efeu.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import de.fzi.efeu.service.ProcessService;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class ProcessApiController implements ProcessApi {

    @Autowired
    private NativeWebRequest request;

    @Autowired
    private ProcessService processService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<String> getHealth() {
        return new ResponseEntity<>("Process management is running!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> fixTrip(final String id) {
        try {
            processService.fixTrip(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<String> plan() {
        try {
            processService.plan();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<String> updateOrders() {
        return null;
    }

    @Override
    public ResponseEntity<String> updateTrips() {
        return null;
    }
}
