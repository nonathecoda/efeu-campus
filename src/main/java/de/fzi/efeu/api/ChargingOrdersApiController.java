package de.fzi.efeu.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-11T12:48:58.805+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Recharging Scheduling.base-path:}")
public class ChargingOrdersApiController implements ChargingOrdersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ChargingOrdersApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<ChargingOrder>> getChargingOrders() {
        return null;
    }
}
