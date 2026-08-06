package lk.ijse.parkingspacesevice.entity;

import jakarta.persistence.*;
import lk.ijse.parkingspacesevice.dto.Enum.SpotStatus;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "parking_spots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String spotId;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer totalSpots;

    @Column(nullable = false)
    private Integer availableSpots;

    @Column(nullable = false)
    private BigDecimal pricePerHour;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotStatus status;
}