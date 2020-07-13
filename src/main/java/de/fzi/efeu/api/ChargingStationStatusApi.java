/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package de.fzi.efeu.api;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import de.fzi.efeu.model.ChargingStationStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-10T14:10:36.203026900+02:00[Europe/Berlin]")

@Validated
@Api(value = "chargingStationStatus", description = "the chargingStationStatus API")
public interface ChargingStationStatusApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /chargingStationStatus/{id}
     *
     * @param id efeu ID (required)
     * @param chargingStationStatus  (optional)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "createChargingStationStatus", notes = "", response = ChargingStationStatus.class, tags={ "ChargingStationStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ChargingStationStatus.class) })
    @RequestMapping(value = "/chargingStationStatus/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ChargingStationStatus> createChargingStationStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id,
            @ApiParam(value = "") @Valid @RequestBody(required = false) ChargingStationStatus chargingStationStatus) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"chargingStationState\" : \"Free\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /chargingStationStatus/{id}
     *
     * @param id efeu ID (required)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "deleteChargingStationStatus", notes = "", response = String.class, tags={ "ChargingStationStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = String.class) })
    @RequestMapping(value = "/chargingStationStatus/{id}",
        produces = { "text/plain" }, 
        method = RequestMethod.DELETE)
    default ResponseEntity<String> deleteChargingStationStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /chargingStationStatus/
     *
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "getChargingStationList", notes = "", response = ChargingStationStatus.class, responseContainer = "List", tags={ "ChargingStationStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ChargingStationStatus.class, responseContainer = "List") })
    @RequestMapping(value = "/chargingStationStatus/",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<ChargingStationStatus>> getChargingStationList() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"chargingStationState\" : \"Free\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /chargingStationStatus/{id}
     *
     * @param id efeu ID (required)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "getChargingStationStatus", notes = "", response = ChargingStationStatus.class, tags={ "ChargingStationStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ChargingStationStatus.class) })
    @RequestMapping(value = "/chargingStationStatus/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ChargingStationStatus> getChargingStationStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"chargingStationState\" : \"Free\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /chargingStationStatus/{id}
     *
     * @param id efeu ID (required)
     * @param chargingStationStatus  (optional)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "updateChargingStationStatus", notes = "", response = ChargingStationStatus.class, tags={ "ChargingStationStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ChargingStationStatus.class) })
    @RequestMapping(value = "/chargingStationStatus/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ChargingStationStatus> updateChargingStationStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id,
            @ApiParam(value = "") @Valid @RequestBody(required = false) ChargingStationStatus chargingStationStatus) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"chargingStationState\" : \"Free\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
