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

import de.fzi.efeu.db.dao.OrderStatusRepository;
import de.fzi.efeu.model.OrderStatus;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-07-01T13:33:10.937421600+02:00[Europe/Berlin]")

@Controller
@RequestMapping("${openapi.Process management.base-path:}")
public class OrderStatusApiController implements OrderStatusApi {

    @Autowired
    private NativeWebRequest request;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<OrderStatus> createOrderStatus(final String id, @Valid final OrderStatus orderStatus) {
        de.fzi.efeu.db.model.OrderStatus dbStatus =
                modelMapper.map(orderStatus, de.fzi.efeu.db.model.OrderStatus.class);
        orderStatusRepository.save(dbStatus);
        return ResponseEntity.ok(orderStatus);
    }

    @Override
    public ResponseEntity<OrderStatus> getOrderStatus(final String id) {
        de.fzi.efeu.db.model.OrderStatus dbStatus = orderStatusRepository.findByOrderId(id);
        OrderStatus chargingStationStatus = modelMapper.map(dbStatus, OrderStatus.class);
        return ResponseEntity.ok(chargingStationStatus);
    }

    @Override
    public ResponseEntity<List<OrderStatus>> getOrderStatusList() {
        List<OrderStatus> statusList =
                Arrays.asList(modelMapper.map(orderStatusRepository.findAll(), OrderStatus[].class));
        return ResponseEntity.ok(statusList);
    }

    @Override
    public ResponseEntity<OrderStatus> updateOrderStatus(final String id, @Valid final OrderStatus orderStatus) {
        de.fzi.efeu.db.model.OrderStatus dbStatus = orderStatusRepository.findByOrderId(id);
        modelMapper.map(orderStatus, dbStatus);
        orderStatusRepository.save(dbStatus);
        return ResponseEntity.ok(orderStatus);
    }
}
