package de.fzi.efeu.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import de.fzi.efeu.db.dao.VehicleStatusRepository;
import de.fzi.efeu.model.VehicleStatus;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class VehicleStatusApiController implements VehicleStatusApi {

    @Autowired
    private NativeWebRequest request;

    @Autowired
    private VehicleStatusRepository vehicleStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<VehicleStatus> createVehicleStatus(final String id,
            @Valid final VehicleStatus vehicleStatus) {
        de.fzi.efeu.db.model.VehicleStatus dbStatus =
                modelMapper.map(vehicleStatus, de.fzi.efeu.db.model.VehicleStatus.class);
        vehicleStatusRepository.save(dbStatus);
        return ResponseEntity.ok(vehicleStatus);
    }

    @Override
    public ResponseEntity<VehicleStatus> getVehicleStatus(final String id) {
        de.fzi.efeu.db.model.VehicleStatus dbStatus = vehicleStatusRepository.findByVehicleId(id);
        VehicleStatus chargingStationStatus = modelMapper.map(dbStatus, VehicleStatus.class);
        return ResponseEntity.ok(chargingStationStatus);
    }

    @Override
    public ResponseEntity<List<VehicleStatus>> getVehicleStatusList() {
        List<VehicleStatus> statusList =
                Arrays.asList(modelMapper.map(vehicleStatusRepository.findAll(), VehicleStatus[].class));
        return ResponseEntity.ok(statusList);
    }

    @Override
    public ResponseEntity<VehicleStatus> updateVehicleStatus(final String id,
            @Valid final VehicleStatus vehicleStatus) {
        de.fzi.efeu.db.model.VehicleStatus dbStatus = vehicleStatusRepository.findByVehicleId(id);
        modelMapper.map(vehicleStatus, dbStatus);
        vehicleStatusRepository.save(dbStatus);
        return ResponseEntity.ok(vehicleStatus);
    }
}
