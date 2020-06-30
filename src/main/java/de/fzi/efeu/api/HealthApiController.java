package de.fzi.efeu.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-06-29T14:44:17.807+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class HealthApiController implements HealthApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public HealthApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
