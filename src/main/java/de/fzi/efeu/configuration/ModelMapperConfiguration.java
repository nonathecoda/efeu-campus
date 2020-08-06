package de.fzi.efeu.configuration;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        configureVehicleStatusMapping(modelMapper);

        return modelMapper;
    }

    private void configureBoxStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.BoxStatus.class, BoxStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(BoxStatus::setId);
                    mapper.map(de.fzi.efeu.model.BoxStatus::getId, BoxStatus::setBoxId);
                    mapper.map(de.fzi.efeu.model.BoxStatus::getMountId, BoxStatus::setMountId);
                    mapper.map(de.fzi.efeu.model.BoxStatus::getVehicleId, BoxStatus::setVehicleId);
                });
        modelMapper.addMappings(new PropertyMap<BoxStatus, de.fzi.efeu.model.BoxStatus>() {
            protected void configure() {
                map().setId(source.getBoxId());
            }
        });
    }

    private void configureChargingStationStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.ChargingStationStatus.class, ChargingStationStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(ChargingStationStatus::setId);
                    mapper.map(de.fzi.efeu.model.ChargingStationStatus::getId, ChargingStationStatus::setChargingStationId);
                });
        modelMapper
                .typeMap(ChargingStationStatus.class, de.fzi.efeu.model.ChargingStationStatus.class)
                .addMappings(mapper -> {
                    mapper.map(ChargingStationStatus::getChargingStationId, de.fzi.efeu.model.ChargingStationStatus::setId);
                });
    }

    private void configureMountStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.MountStatus.class, MountStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(MountStatus::setId);
                    mapper.map(de.fzi.efeu.model.MountStatus::getId, MountStatus::setMountId);
                });
        modelMapper.addMappings(new PropertyMap<MountStatus, de.fzi.efeu.model.MountStatus>() {
            protected void configure() {
                map().setId(source.getMountId());
            }
        });
    }

    private void configureVehicleStatusMapping(ModelMapper modelMapper) {
        modelMapper
                .typeMap(de.fzi.efeu.model.VehicleStatus.class, VehicleStatus.class)
                .addMappings(mapper -> {
                    mapper.skip(VehicleStatus::setId);
                    mapper.map(de.fzi.efeu.model.VehicleStatus::getId, VehicleStatus::setVehicleId);
                });
        modelMapper.addMappings(new PropertyMap<VehicleStatus, de.fzi.efeu.model.VehicleStatus>() {
            protected void configure() {
                map().setId(source.getVehicleId());
            }
        });
    }
}
