package lk.ijse.parkingspacesevice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDashboardDTO {
    private String ownerId;
    private Integer totalZones;
    private Integer totalCapacity;
    private Integer totalAvailableSpots;
    private Integer totalOccupiedSpots;
    private List<ParkingSpotResponseDTO> zones;
}