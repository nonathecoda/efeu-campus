package de.fzi.efeu.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-12T13:07:36.908610100+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Recharging Scheduling.base-path:}")
public class GetInitialChargingOrdersApiController implements GetInitialChargingOrdersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public GetInitialChargingOrdersApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
