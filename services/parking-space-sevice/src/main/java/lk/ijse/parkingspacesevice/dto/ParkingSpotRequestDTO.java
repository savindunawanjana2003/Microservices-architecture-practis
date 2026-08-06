package lk.ijse.parkingspacesevice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
@Getter
public class ParkingSpotRequestDTO {
    private String ownerId;
    private String title;
    private String location;
    private Integer totalSpots;
    private BigDecimal pricePerHour;
}