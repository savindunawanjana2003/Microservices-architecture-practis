package lk.ijse.parkingspacesevice.dto;


import lk.ijse.parkingspacesevice.dto.Enum.SpotStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParkingSpotResponseDTO {
    private String spotId;
    private String ownerId;
    private String title;
    private String location;
    private Integer totalSpots;
    private Integer availableSpots;
    private BigDecimal pricePerHour;
    private SpotStatus status;
}