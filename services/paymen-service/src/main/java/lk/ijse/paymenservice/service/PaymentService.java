package lk.ijse.paymenservice.service;


import lk.ijse.paymenservice.dto.PaymentRequestDTO;
import lk.ijse.paymenservice.dto.ReceiptDTO;

public interface PaymentService {
    ReceiptDTO processPayment(PaymentRequestDTO paymentRequestDTO);

    ReceiptDTO getReceiptByPaymentId(String paymentId);

    ReceiptDTO getReceiptByBookingId(String bookingId);
}