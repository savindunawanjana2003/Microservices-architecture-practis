package lk.ijse.paymenservice.api;

import lk.ijse.paymenservice.dto.PaymentRequestDTO;
import lk.ijse.paymenservice.dto.ReceiptDTO;
import lk.ijse.paymenservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Process Payment
    @PostMapping("/process")
    public ResponseEntity<ReceiptDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        ReceiptDTO receipt = paymentService.processPayment(paymentRequestDTO);
        return new ResponseEntity<>(receipt, HttpStatus.CREATED);
    }

    // Generate/Get Receipt by Payment ID
    @GetMapping("/receipt/{paymentId}")
    public ResponseEntity<ReceiptDTO> getReceiptByPaymentId(@PathVariable String paymentId) {
        ReceiptDTO receipt = paymentService.getReceiptByPaymentId(paymentId);
        return ResponseEntity.ok(receipt);
    }

    // Generate/Get Receipt by Booking ID
    @GetMapping("/receipt/booking/{bookingId}")
    public ResponseEntity<ReceiptDTO> getReceiptByBookingId(@PathVariable String bookingId) {
        ReceiptDTO receipt = paymentService.getReceiptByBookingId(bookingId);
        return ResponseEntity.ok(receipt);
    }
}