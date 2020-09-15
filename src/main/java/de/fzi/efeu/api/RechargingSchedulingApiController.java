package de.fzi.efeu.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;

import de.fzi.efeu.service.RechargingService;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-15T09:15:36.283107100+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Recharging Scheduling.base-path:}")
public class RechargingSchedulingApiController implements RechargingSchedulingApi {

    private final NativeWebRequest request;

    @Autowired
    private RechargingService rechargingService;

    @org.springframework.beans.factory.annotation.Autowired
    public RechargingSchedulingApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> scheduleRechargingOrders() {
        try {
            rechargingService.scheduleRechargingOrders();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(null);
    }
}
