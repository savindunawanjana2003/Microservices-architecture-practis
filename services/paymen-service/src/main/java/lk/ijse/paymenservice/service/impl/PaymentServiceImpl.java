package lk.ijse.paymenservice.service.impl;


import lk.ijse.paymenservice.dto.PaymentRequestDTO;
import lk.ijse.paymenservice.dto.ReceiptDTO;
import lk.ijse.paymenservice.entity.Payment;
import lk.ijse.paymenservice.repo.PaymentRepository;
import lk.ijse.paymenservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public ReceiptDTO processPayment(PaymentRequestDTO request) {
        // Mock Card Validation (Card Number: 16 digits, Expiry: MM/YY, CVV: 3 digits)
        if (!validateMockCard(request.getCardNumber(), request.getExpiryDate(), request.getCvv())) {
            throw new RuntimeException("Invalid Payment Details!");
        }

        Payment payment = Payment.builder()
                .bookingId(request.getBookingId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .paymentStatus("SUCCESS")
                .transactionTime(LocalDateTime.now())
                .transactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return mapToReceiptDTO(savedPayment);
    }

    @Override
    public ReceiptDTO getReceiptByPaymentId(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Receipt/Payment not found for ID: " + paymentId));
        return mapToReceiptDTO(payment);
    }

    @Override
    public ReceiptDTO getReceiptByBookingId(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Receipt/Payment not found for Booking ID: " + bookingId));
        return mapToReceiptDTO(payment);
    }

    private boolean validateMockCard(String cardNumber, String expiryDate, String cvv) {
        return cardNumber != null && cardNumber.matches("\\d{16}") &&
                expiryDate != null && expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}") &&
                cvv != null && cvv.matches("\\d{3}");
    }

    private ReceiptDTO mapToReceiptDTO(Payment payment) {
        return ReceiptDTO.builder()
                .receiptId("RCP-" + payment.getPaymentId().substring(0, 8).toUpperCase())
                .paymentId(payment.getPaymentId())
                .bookingId(payment.getBookingId())
                .userId(payment.getUserId())
                .amountPaid(payment.getAmount())
                .status(payment.getPaymentStatus())
                .timestamp(payment.getTransactionTime())
                .transactionReference(payment.getTransactionRef())
                .build();
    }
}