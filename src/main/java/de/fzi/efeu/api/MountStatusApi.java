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

import de.fzi.efeu.model.MountStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Validated
@Api(value = "mountStatus", description = "the mountStatus API")
public interface MountStatusApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /mountStatus/{id}
     *
     * @param id efeu ID (required)
     * @param mountStatus  (optional)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "createMountStatus", notes = "", response = MountStatus.class, tags={ "MountStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = MountStatus.class) })
    @RequestMapping(value = "/mountStatus/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<MountStatus> createMountStatus(@ApiParam(value = "efeu ID",required=true) @PathVariable("id") String id,@ApiParam(value = ""  )  @Valid @RequestBody(required = false) MountStatus mountStatus) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountState\" : \"Free\", \"id\" : \"id\", \"boxId\" : \"boxId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /mountStatus/{id}
     *
     * @param id efeu ID (required)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "getMountStatus", notes = "", response = MountStatus.class, tags={ "MountStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = MountStatus.class) })
    @RequestMapping(value = "/mountStatus/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<MountStatus> getMountStatus(@ApiParam(value = "efeu ID",required=true) @PathVariable("id") String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountState\" : \"Free\", \"id\" : \"id\", \"boxId\" : \"boxId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /mountStatus/
     *
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "getMountStatusList", notes = "", response = MountStatus.class, responseContainer = "List", tags={ "MountStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = MountStatus.class, responseContainer = "List") })
    @RequestMapping(value = "/mountStatus/",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<MountStatus>> getMountStatusList() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountState\" : \"Free\", \"id\" : \"id\", \"boxId\" : \"boxId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /mountStatus/{id}
     *
     * @param id efeu ID (required)
     * @param mountStatus  (optional)
     * @return Success (status code 200)
     */
    @ApiOperation(value = "", nickname = "updateMountStatus", notes = "", response = MountStatus.class, tags={ "MountStatus", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = MountStatus.class) })
    @RequestMapping(value = "/mountStatus/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<MountStatus> updateMountStatus(@ApiParam(value = "efeu ID",required=true) @PathVariable("id") String id,@ApiParam(value = ""  )  @Valid @RequestBody(required = false) MountStatus mountStatus) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mountState\" : \"Free\", \"id\" : \"id\", \"boxId\" : \"boxId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
