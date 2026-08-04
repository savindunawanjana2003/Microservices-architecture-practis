package lk.ijse.vehiclesevice.entity;


import jakarta.persistence.*;
import lk.ijse.vehiclesevice.dto.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vehicle {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String licensePlateNumber;

    private String model;
    private String color;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private String userId; // User Service eke Driver ID eka
}