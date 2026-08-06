package lk.ijse.parkingspacesevice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private String bookingId;
    private String spotId;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // CONFIRMED, COMPLETED, CANCELLED
    private BigDecimal amount;
}