package lk.ijse.parkingspacesevice.api;

import lk.ijse.parkingspacesevice.dto.ParkingSpotRequestDTO;
import lk.ijse.parkingspacesevice.dto.ParkingSpotResponseDTO;
import lk.ijse.parkingspacesevice.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-spaces")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService service;

    // 1. Add New Parking Space
    @PostMapping
    public ResponseEntity<ParkingSpotResponseDTO> addSpot(@RequestBody ParkingSpotRequestDTO dto) {
        return new ResponseEntity<>(service.addParkingSpot(dto), HttpStatus.CREATED);
    }

    // 2. Update Parking Space Details
    @PutMapping("/{spotId}")
    public ResponseEntity<ParkingSpotResponseDTO> updateSpot(
            @PathVariable String spotId,
            @RequestBody ParkingSpotRequestDTO dto) {
        return ResponseEntity.ok(service.updateParkingSpot(spotId, dto));
    }

    // 3. Delete Parking Space
    @DeleteMapping("/{spotId}")
    public ResponseEntity<String> deleteSpot(@PathVariable String spotId) {
        service.deleteParkingSpot(spotId);
        return ResponseEntity.ok("Parking Space deleted successfully!");
    }

    // 4. Get Space Details by Spot ID
    @GetMapping("/{spotId}")
    public ResponseEntity<ParkingSpotResponseDTO> getSpotById(@PathVariable String spotId) {
        return ResponseEntity.ok(service.getParkingSpotById(spotId));
    }

    // 5. Get All Spaces owned by a specific Owner
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingSpotResponseDTO>> getSpotsByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(service.getSpotsByOwnerId(ownerId));
    }
}