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

import de.fzi.efeu.db.dao.ChargingStationStatusRepository;
import de.fzi.efeu.model.ChargingStationStatus;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class ChargingStationStatusApiController implements ChargingStationStatusApi {

    @Autowired
    private NativeWebRequest request;

    @Autowired
    private ChargingStationStatusRepository chargingStationStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<ChargingStationStatus> createChargingStationStatus(final String id,
            @Valid final ChargingStationStatus chargingStationStatus) {
        de.fzi.efeu.db.model.ChargingStationStatus dbStatus =
                modelMapper.map(chargingStationStatus, de.fzi.efeu.db.model.ChargingStationStatus.class);
        chargingStationStatusRepository.save(dbStatus);
        return ResponseEntity.ok(chargingStationStatus);
    }

    @Override
    public ResponseEntity<List<ChargingStationStatus>> getChargingStationList() {
        List<ChargingStationStatus> statusList =
                Arrays.asList(modelMapper.map(chargingStationStatusRepository.findAll(), ChargingStationStatus[].class));
        return ResponseEntity.ok(statusList);
    }

    @Override
    public ResponseEntity<ChargingStationStatus> getChargingStationStatus(final String id) {
        de.fzi.efeu.db.model.ChargingStationStatus dbStatus = chargingStationStatusRepository.findByChargingStationId(id);
        ChargingStationStatus chargingStationStatus = modelMapper.map(dbStatus, ChargingStationStatus.class);
        return ResponseEntity.ok(chargingStationStatus);
    }

    @Override
    public ResponseEntity<ChargingStationStatus> updateChargingStationStatus(final String id,
            @Valid final ChargingStationStatus chargingStationStatus) {
        de.fzi.efeu.db.model.ChargingStationStatus dbStatus = chargingStationStatusRepository.findByChargingStationId(id);
        modelMapper.map(chargingStationStatus, dbStatus);
        chargingStationStatusRepository.save(dbStatus);
        return ResponseEntity.ok(chargingStationStatus);
    }
}
