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

import de.fzi.efeu.db.dao.MountStatusRepository;
import de.fzi.efeu.model.MountStatus;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class MountStatusApiController implements MountStatusApi {

    @Autowired
    private NativeWebRequest request;

    @Autowired
    private MountStatusRepository mountStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<MountStatus> createMountStatus(final String id, @Valid final MountStatus mountStatus) {
        de.fzi.efeu.db.model.MountStatus dbStatus =
                modelMapper.map(mountStatus, de.fzi.efeu.db.model.MountStatus.class);
        mountStatusRepository.save(dbStatus);
        return ResponseEntity.ok(mountStatus);
    }

    @Override
    public ResponseEntity<MountStatus> getMountStatus(final String id) {
        de.fzi.efeu.db.model.MountStatus dbStatus = mountStatusRepository.findByMountId(id);
        MountStatus chargingStationStatus = modelMapper.map(dbStatus, MountStatus.class);
        return ResponseEntity.ok(chargingStationStatus);
    }

    @Override
    public ResponseEntity<List<MountStatus>> getMountStatusList() {
        List<MountStatus> mountStatusList =
                Arrays.asList(modelMapper.map(mountStatusRepository.findAll(), MountStatus[].class));
        return ResponseEntity.ok(mountStatusList);
    }

    @Override
    public ResponseEntity<MountStatus> updateMountStatus(final String id, @Valid final MountStatus mountStatus) {
        de.fzi.efeu.db.model.MountStatus dbStatus = mountStatusRepository.findByMountId(id);
        modelMapper.map(mountStatus, dbStatus);
        mountStatusRepository.save(dbStatus);
        return ResponseEntity.ok(mountStatus);
    }
}
