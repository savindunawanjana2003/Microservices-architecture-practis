package lk.ijse.paymenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDTO {
    private String receiptId;
    private String paymentId;
    private String bookingId;
    private String userId;
    private Double amountPaid;
    private String status;
    private LocalDateTime timestamp;
    private String transactionReference;
}