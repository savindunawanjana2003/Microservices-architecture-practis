package lk.ijse.vehiclesevice.entity;

import jakarta.persistence.*;
//import lk.ijse.vehiclesevice.dto.Enum.Status;
import lk.ijse.vehiclesevice.dto.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class VehicleLog {

    @Id
    private String logId;

    @Column(nullable = false)
    private String vehicleId;

    @Column(nullable = false)
    private String parkingSpotId;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Enumerated(EnumType.STRING)
    private Status status;
    // IN_PARKING, EXITED
}