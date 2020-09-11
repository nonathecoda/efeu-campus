/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package de.fzi.efeu.api;

import de.fzi.efeu.model.ChargingOrder;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-11T12:48:58.805+02:00[Europe/Berlin]")

@Validated
@Api(value = "ChargingOrders", description = "the ChargingOrders API")
public interface ChargingOrdersApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /ChargingOrders/ : get recharging order
     *
     * @return Success (status code 200)
     *         or Failure (status code 400)
     */
    @ApiOperation(value = "get recharging order", nickname = "getChargingOrders", notes = "", response = ChargingOrder.class, responseContainer = "List", tags={ "Recharging Scheduling", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ChargingOrder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Failure") })
    @RequestMapping(value = "/ChargingOrders/",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<ChargingOrder>> getChargingOrders() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"charging station Id\" : 6, \"connection_time\" : \"2000-01-23T04:56:07.000+00:00\", \"amount_energy\" : \"\", \"vehicleId\" : 0, \"disconnection_time\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
