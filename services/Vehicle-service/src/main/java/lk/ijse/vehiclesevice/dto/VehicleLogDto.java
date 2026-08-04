package lk.ijse.vehiclesevice.dto;

import lk.ijse.vehiclesevice.dto.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleLogDto {
    private String logId;
    private String vehicleId;
    private String parkingSpotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Status status;
}
