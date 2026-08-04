package lk.ijse.vehiclesevice.service;


import lk.ijse.vehiclesevice.dto.VehicleDto;
import lk.ijse.vehiclesevice.dto.VehicleLogDto;

import java.util.List;

public interface VehicleService {
    VehicleDto registerVehicle(VehicleDto vehicleDTO);

    VehicleDto updateVehicle(String id, VehicleDto vehicleDTO);

    VehicleDto getVehicleById(String id);

    List<VehicleDto> getVehiclesByUserId(String userId);

    // Simulated IoT Entry/Exit Tracking
    VehicleLogDto logVehicleEntry(String vehicleId, String parkingSpotId);

    VehicleLogDto logVehicleExit(String vehicleId);

    List<VehicleLogDto> getLogsByVehicleId(String vehicleId);
}

