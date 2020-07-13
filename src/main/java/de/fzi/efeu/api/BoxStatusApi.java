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

import de.fzi.efeu.model.BoxStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-10T14:10:36.203026900+02:00[Europe/Berlin]")

@Validated
@Api(value = "boxStatus", description = "the boxStatus API")
public interface BoxStatusApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /boxStatus/{id}
     *
     * @param id efeu ID (required)
     * @param boxStatus  (optional)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "createBoxStatus", notes = "", response = BoxStatus.class, tags={ "BoxStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = BoxStatus.class) })
    @RequestMapping(value = "/boxStatus/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<BoxStatus> createBoxStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id,
            @ApiParam(value = "") @Valid @RequestBody(required = false) BoxStatus boxStatus) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountId\" : \"mountId\", \"boxState\" : \"Free\", \"latitude\" : 0.8008282, \"boxLoad\" : \"Empty\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"longitude\" : 6.0274563, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /boxStatus/{id}
     *
     * @param id efeu ID (required)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "deleteBoxStatus", notes = "", response = String.class, tags={ "BoxStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = String.class) })
    @RequestMapping(value = "/boxStatus/{id}",
        produces = { "text/plain" }, 
        method = RequestMethod.DELETE)
    default ResponseEntity<String> deleteBoxStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /boxStatus/{id}
     *
     * @param id efeu ID (required)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "getBoxStatus", notes = "", response = BoxStatus.class, tags={ "BoxStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = BoxStatus.class) })
    @RequestMapping(value = "/boxStatus/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<BoxStatus> getBoxStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountId\" : \"mountId\", \"boxState\" : \"Free\", \"latitude\" : 0.8008282, \"boxLoad\" : \"Empty\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"longitude\" : 6.0274563, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /boxStatus/
     *
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "getBoxStatusList", notes = "", response = BoxStatus.class, responseContainer = "List", tags={ "BoxStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = BoxStatus.class, responseContainer = "List") })
    @RequestMapping(value = "/boxStatus/",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<BoxStatus>> getBoxStatusList() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountId\" : \"mountId\", \"boxState\" : \"Free\", \"latitude\" : 0.8008282, \"boxLoad\" : \"Empty\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"longitude\" : 6.0274563, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /boxStatus/{id}
     *
     * @param id efeu ID (required)
     * @param boxStatus  (optional)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "updateBoxStatus", notes = "", response = BoxStatus.class, tags={ "BoxStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = BoxStatus.class) })
    @RequestMapping(value = "/boxStatus/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<BoxStatus> updateBoxStatus(
            @ApiParam(value = "efeu ID", required = true) @PathVariable("id") String id,
            @ApiParam(value = "") @Valid @RequestBody(required = false) BoxStatus boxStatus) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountId\" : \"mountId\", \"boxState\" : \"Free\", \"latitude\" : 0.8008282, \"boxLoad\" : \"Empty\", \"id\" : \"id\", \"vehicleId\" : \"vehicleId\", \"longitude\" : 6.0274563, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}