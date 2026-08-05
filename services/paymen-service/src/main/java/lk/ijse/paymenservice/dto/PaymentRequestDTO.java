package lk.ijse.paymenservice.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String bookingId;
    private String userId;
    private Double amount;
    private String cardNumber;
    private String expiryDate; // MM/YY
    private String cvv;
}