//package lk.ijse.vehiclesevice.api;
//
//import lk.ijse.vehiclesevice.dto.VehicleDto;
//import lk.ijse.vehiclesevice.dto.VehicleLogDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/vehicles")
//@RequiredArgsConstructor
//public class VehicleController {
//
//    private final lk.ijse.vehiclesevice.service.VehicleService vehicleService;
//
//
//    //okkk
//    @PostMapping("save")
//    public ResponseEntity<VehicleDto> registerVehicle(@RequestBody VehicleDto vehicleDTO) {
//        return new ResponseEntity<>(vehicleService.registerVehicle(vehicleDTO), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable String id) {
//        return ResponseEntity.ok(vehicleService.getVehicleById(id));
//    }
//
//    //  okkk
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<VehicleDto>> getVehiclesByUserId(@PathVariable String userId) {
//        return ResponseEntity.ok(vehicleService.getVehiclesByUserId(userId));
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable String id, @RequestBody VehicleDto vehicleDTO) {
//        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleDTO));
//    }
//
//    @PostMapping("/{vehicleId}/entry")
//    public ResponseEntity<VehicleLogDto> logEntry(@PathVariable String vehicleId, @RequestParam String parkingSpotId) {
//        return ResponseEntity.ok(vehicleService.logVehicleEntry(vehicleId, parkingSpotId));
//    }
//
//    @PostMapping("/{vehicleId}/exit")
//    public ResponseEntity<VehicleLogDto> logExit(@PathVariable String vehicleId) {
//        return ResponseEntity.ok(vehicleService.logVehicleExit(vehicleId));
//    }
//
//    @GetMapping("/{vehicleId}/logs")
//    public ResponseEntity<List<VehicleLogDto>> getVehicleLogs(@PathVariable String vehicleId) {
//        return ResponseEntity.ok(vehicleService.getLogsByVehicleId(vehicleId));
//    }
//}


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

    // okkk
    @PostMapping("save")
    public ResponseEntity<?> registerVehicle(@RequestBody VehicleDto vehicleDTO) {
        try {
            return new ResponseEntity<>(vehicleService.registerVehicle(vehicleDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // okkk
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getVehiclesByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(vehicleService.getVehiclesByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //Okkkk
    @PostMapping("/{vehicleId}/entry")
    public ResponseEntity<?> logEntry(@PathVariable String vehicleId, @RequestParam String parkingSpotId) {
        try {
            return ResponseEntity.ok(vehicleService.logVehicleEntry(vehicleId, parkingSpotId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //okkk
    @PostMapping("/{vehicleId}/exit")
    public ResponseEntity<?> logExit(@PathVariable String vehicleId) {
        try {
            return ResponseEntity.ok(vehicleService.logVehicleExit(vehicleId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//============================================================

    //okk
    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable String id, @RequestBody VehicleDto vehicleDTO) {
        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/{vehicleId}/logs")
    public ResponseEntity<?> getVehicleLogs(@PathVariable String vehicleId) {
        try {

            List<VehicleLogDto> vehicleDtos = vehicleService.getLogsByVehicleId(vehicleId);

            if (vehicleDtos.isEmpty()) {
                throw new RuntimeException("any Logs not avelable");
            }

            return ResponseEntity.ok(vehicleDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}