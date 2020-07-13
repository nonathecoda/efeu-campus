package de.fzi.efeu.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import de.fzi.efeu.db.dao.BoxStatusRepository;
import de.fzi.efeu.model.BoxStatus;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class BoxStatusApiController implements BoxStatusApi {

    private final BoxStatusRepository boxStatusRepository;

    private final NativeWebRequest request;

    private final ModelMapper modelMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public BoxStatusApiController(NativeWebRequest request,
            BoxStatusRepository boxStatusRepository,
            ModelMapper modelMapper) {
        this.request = request;
        this.boxStatusRepository = boxStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<BoxStatus>> getBoxStatusList() {
        List<BoxStatus> boxStatusList = Arrays.asList(modelMapper.map(boxStatusRepository.findAll(), BoxStatus[].class));
        return ResponseEntity.ok(boxStatusList);
    }

    @Override
    @Transactional
    public ResponseEntity<BoxStatus> createBoxStatus(final String id, @Valid final BoxStatus boxStatus) {
        de.fzi.efeu.db.model.BoxStatus dbBoxStatus = modelMapper.map(boxStatus, de.fzi.efeu.db.model.BoxStatus.class);
        boxStatusRepository.save(dbBoxStatus);
        return ResponseEntity.ok(boxStatus);
    }

    @Override
    @Transactional
    public ResponseEntity<BoxStatus> getBoxStatus(final String id) {
        de.fzi.efeu.db.model.BoxStatus dbBoxStatus = boxStatusRepository.findByBoxId(id);
        BoxStatus boxStatus = modelMapper.map(dbBoxStatus, BoxStatus.class);
        return ResponseEntity.ok(boxStatus);
    }

    @Override
    @Transactional
    public ResponseEntity<BoxStatus> updateBoxStatus(final String id, @Valid final BoxStatus boxStatus) {
        de.fzi.efeu.db.model.BoxStatus dbBoxStatus = boxStatusRepository.findByBoxId(id);
        modelMapper.map(boxStatus, dbBoxStatus);
        boxStatusRepository.save(dbBoxStatus);
        return ResponseEntity.ok(boxStatus);
    }

    @Override
    public ResponseEntity<String> deleteBoxStatus(final String id) {
        de.fzi.efeu.db.model.BoxStatus dbBoxStatus = boxStatusRepository.findByBoxId(id);
        boxStatusRepository.delete(dbBoxStatus);
        return ResponseEntity.ok("");
    }
}
