package lk.ijse.parkingspacesevice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerRevenueReportDTO {
    private String ownerId;
    private BigDecimal totalEarnings;
    private Integer totalBookingsCount;
    private List<BookingDTO> bookingHistory;
}