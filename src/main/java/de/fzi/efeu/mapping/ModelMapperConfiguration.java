package de.fzi.efeu.mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.fzi.efeu.db.model.BoxStatus;
import de.fzi.efeu.db.model.ChargingStationStatus;
import de.fzi.efeu.db.model.MountStatus;
import de.fzi.efeu.db.model.OrderStatus;
import de.fzi.efeu.db.model.VehicleStatus;

@Configuration
public class ModelMapperConfiguration {

    private Converter<OffsetDateTime, LocalDateTime> toLocalDateTime = new Converter<OffsetDateTime, LocalDateTime>() {
        public LocalDateTime convert(MappingContext<OffsetDateTime, LocalDateTime> context) {
            return context.getSource().toLocalDateTime();
        }
    };

    private Converter<LocalDateTime, OffsetDateTime> toOffsetDateTime = new Converter<LocalDateTime, OffsetDateTime>() {
        public OffsetDateTime convert(MappingContext<LocalDateTime, OffsetDateTime> context) {
            return OffsetDateTime.of(context.getSource(), ZoneId.of("Europe/Berlin").getRules().getOffset(context.getSource()));
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(toOffsetDateTime);
        modelMapper.addConverter(toLocalDateTime);

        configureBoxStatusMapping(modelMapper);
        configureChargingStationStatusMapping(modelMapper);
        configureMountStatusMapping(modelMapper);
        configureOrderStatusMapping(modelMapper);
        configureVehicleStatusMapping(modelMapper);

        return modelMapper;
    }

    public void configureBoxStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.BoxStatus.class, BoxStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(BoxStatus::setId);
                    mapper.map(de.fzi.efeu.model.BoxStatus::getId, BoxStatus::setBoxId);
                    mapper.using(toLocalDateTime).map(de.fzi.efeu.model.BoxStatus::getTimestamp, BoxStatus::setTimestamp);
                });
        modelMapper
                .typeMap(BoxStatus.class, de.fzi.efeu.model.BoxStatus.class)
                .addMappings(mapper -> {
                    mapper.map(BoxStatus::getBoxId, de.fzi.efeu.model.BoxStatus::setId);
                    mapper.using(toOffsetDateTime).map(BoxStatus::getTimestamp, de.fzi.efeu.model.BoxStatus::setTimestamp);
                });
    }

    public void configureChargingStationStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.ChargingStationStatus.class, ChargingStationStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(ChargingStationStatus::setId);
                    mapper.map(de.fzi.efeu.model.ChargingStationStatus::getId, ChargingStationStatus::setChargingStationId);
                    mapper.using(toLocalDateTime).map(de.fzi.efeu.model.ChargingStationStatus::getTimestamp, ChargingStationStatus::setTimestamp);
                });
        modelMapper
                .typeMap(ChargingStationStatus.class, de.fzi.efeu.model.ChargingStationStatus.class)
                .addMappings(mapper -> {
                    mapper.map(ChargingStationStatus::getChargingStationId, de.fzi.efeu.model.ChargingStationStatus::setId);
                    mapper.using(toOffsetDateTime).map(ChargingStationStatus::getTimestamp, de.fzi.efeu.model.ChargingStationStatus::setTimestamp);
                });
    }

    public void configureMountStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.MountStatus.class, MountStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(MountStatus::setId);
                    mapper.map(de.fzi.efeu.model.MountStatus::getId, MountStatus::setMountId);
                    mapper.using(toLocalDateTime).map(de.fzi.efeu.model.MountStatus::getTimestamp, MountStatus::setTimestamp);
                });
        modelMapper
                .typeMap(MountStatus.class, de.fzi.efeu.model.MountStatus.class)
                .addMappings(mapper -> {
                    mapper.map(MountStatus::getMountId, de.fzi.efeu.model.MountStatus::setId);
                    mapper.using(toOffsetDateTime).map(MountStatus::getTimestamp, de.fzi.efeu.model.MountStatus::setTimestamp);
                });
    }

    public void configureOrderStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.OrderStatus.class, OrderStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(OrderStatus::setId);
                    mapper.map(de.fzi.efeu.model.OrderStatus::getId, OrderStatus::setOrderId);
                    mapper.using(toLocalDateTime).map(de.fzi.efeu.model.OrderStatus::getTimestamp, OrderStatus::setTimestamp);
                });
        modelMapper
                .typeMap(OrderStatus.class, de.fzi.efeu.model.OrderStatus.class)
                .addMappings(mapper -> {
                    mapper.map(OrderStatus::getOrderId, de.fzi.efeu.model.OrderStatus::setId);
                    mapper.using(toOffsetDateTime).map(OrderStatus::getTimestamp, de.fzi.efeu.model.OrderStatus::setTimestamp);
                });
    }

    public void configureVehicleStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.VehicleStatus.class, VehicleStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(VehicleStatus::setId);
                    mapper.map(de.fzi.efeu.model.VehicleStatus::getId, VehicleStatus::setVehicleId);
                    mapper.using(toLocalDateTime).map(de.fzi.efeu.model.VehicleStatus::getTimestamp, VehicleStatus::setTimestamp);
                });
        modelMapper
                .typeMap(VehicleStatus.class, de.fzi.efeu.model.VehicleStatus.class)
                .addMappings(mapper -> {
                    mapper.map(VehicleStatus::getVehicleId, de.fzi.efeu.model.VehicleStatus::setId);
                    mapper.using(toOffsetDateTime).map(VehicleStatus::getTimestamp, de.fzi.efeu.model.VehicleStatus::setTimestamp);
                });
    }
}
