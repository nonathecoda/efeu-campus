package de.fzi.efeu.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-01-26T15:27:31.942536300+01:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Recharging Scheduling.base-path:}")
public class RechargingSchedulingApiController implements RechargingSchedulingApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RechargingSchedulingApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
