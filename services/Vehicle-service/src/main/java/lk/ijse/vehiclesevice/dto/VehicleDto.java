package lk.ijse.vehiclesevice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lk.ijse.vehiclesevice.dto.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDto {
    private String id;
    private String licensePlateNumber;
    private String model;
    private String color;
    private VehicleType vehicleType;
    private String userId;
}
