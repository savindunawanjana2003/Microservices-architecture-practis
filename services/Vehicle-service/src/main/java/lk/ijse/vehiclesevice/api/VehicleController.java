package lk.ijse.vehiclesevice.api;

import lk.ijse.vehiclesevice.dto.VehicleDto;
import lk.ijse.vehiclesevice.dto.VehicleLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final lk.ijse.vehiclesevice.service.VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDto> registerVehicle(@RequestBody VehicleDto vehicleDTO) {
        return new ResponseEntity<>(vehicleService.registerVehicle(vehicleDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable String id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehicleDto>> getVehiclesByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByUserId(userId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable String id, @RequestBody VehicleDto vehicleDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleDTO));
    }

    @PostMapping("/{vehicleId}/entry")
    public ResponseEntity<VehicleLogDto> logEntry(@PathVariable String vehicleId, @RequestParam String parkingSpotId) {
        return ResponseEntity.ok(vehicleService.logVehicleEntry(vehicleId, parkingSpotId));
    }

    @PostMapping("/{vehicleId}/exit")
    public ResponseEntity<VehicleLogDto> logExit(@PathVariable String vehicleId) {
        return ResponseEntity.ok(vehicleService.logVehicleExit(vehicleId));
    }

    @GetMapping("/{vehicleId}/logs")
    public ResponseEntity<List<VehicleLogDto>> getVehicleLogs(@PathVariable String vehicleId) {
        return ResponseEntity.ok(vehicleService.getLogsByVehicleId(vehicleId));
    }
}