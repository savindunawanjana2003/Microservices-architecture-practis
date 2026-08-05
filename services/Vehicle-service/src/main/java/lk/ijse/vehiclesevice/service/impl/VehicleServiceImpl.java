package lk.ijse.vehiclesevice.service.impl;

import lk.ijse.vehiclesevice.dto.Enum.Status;
import lk.ijse.vehiclesevice.dto.VehicleDto;
import lk.ijse.vehiclesevice.dto.VehicleLogDto;
import lk.ijse.vehiclesevice.entity.Vehicle;
import lk.ijse.vehiclesevice.entity.VehicleLog;
import lk.ijse.vehiclesevice.repo.VehicleLogRepository;
import lk.ijse.vehiclesevice.repo.VehicleRepository;
import lk.ijse.vehiclesevice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleLogRepository vehicleLogRepository;

    @Override
    public VehicleDto registerVehicle(VehicleDto dto) {
        if (vehicleRepository.existsByLicensePlateNumber(dto.getLicensePlateNumber())) {
            throw new RuntimeException("Vehicle plate number already registered!");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID().toString());
        vehicle.setLicensePlateNumber(dto.getLicensePlateNumber());
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setUserId(dto.getUserId());

        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToDTO(saved);
    }

    @Override
    public VehicleDto updateVehicle(String id, VehicleDto dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found!"));

        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());
        vehicle.setVehicleType(dto.getVehicleType());

        return mapToDTO(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleDto getVehicleById(String id) {
        return vehicleRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Vehicle not found!"));
    }

    @Override
    public List<VehicleDto> getVehiclesByUserId(String userId) {
        return vehicleRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public VehicleLogDto logVehicleEntry(String vehicleId, String parkingSpotId) {
        VehicleLog log = new VehicleLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setVehicleId(vehicleId);
        log.setParkingSpotId(parkingSpotId);
        log.setEntryTime(LocalDateTime.now());
        log.setStatus(Status.valueOf("IN_PARKING"));

        VehicleLog savedLog = vehicleLogRepository.save(log);
        return mapToLogDTO(savedLog);
    }

    @Override
    public VehicleLogDto logVehicleExit(String vehicleId) {
        VehicleLog log = vehicleLogRepository.findByVehicleIdAndStatus(vehicleId, Status.IN_PARKING)
                .orElseThrow(() -> new RuntimeException("No active parking log found for this vehicle!"));

        log.setExitTime(LocalDateTime.now());
        log.setStatus(Status.valueOf("EXITED"));

        VehicleLog updatedLog = vehicleLogRepository.save(log);
        return mapToLogDTO(updatedLog);
    }

    @Override
    public List<VehicleLogDto> getLogsByVehicleId(String vehicleId) {
        return vehicleLogRepository.findByVehicleId(vehicleId)
                .stream()
                .map(this::mapToLogDTO)
                .toList();
    }

    private VehicleDto mapToDTO(Vehicle v) {
        return new VehicleDto(
                v.getId(),
                v.getLicensePlateNumber(),
                v.getModel(),
                v.getColor(),
                v.getVehicleType(),
                v.getUserId()
        );
    }

    private VehicleLogDto mapToLogDTO(VehicleLog log) {
        return new VehicleLogDto(
                log.getLogId(),
                log.getVehicleId(),
                log.getParkingSpotId(),
                log.getEntryTime(),
                log.getExitTime(),
                log.getStatus()
        );
    }
}