package com.arka.payments.controllers;

import com.arka.payments.models.Payment;
import com.arka.payments.resources.PaymentAmount;
import com.arka.payments.resources.PaymentRefund;
import com.arka.payments.resources.PaymentRequest;
import com.arka.payments.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest request) {
        String transactionId = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction Successfully initiated. ID: "+transactionId);
    }

    @PutMapping("/accept/{transactionId}")
    public ResponseEntity<String> acceptPayment(
            @PathVariable String transactionId, @RequestBody PaymentAmount amount) {
        try {
            String updatedId = paymentService.acceptPayment(transactionId, amount);
            return ResponseEntity.ok("Payment accepted successfully. ID: " + updatedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> cancelPayment(@PathVariable String transactionId) {
        try {
            paymentService.cancelPayment(transactionId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {

            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/refunded/{orderId}")
    public ResponseEntity<PaymentRefund> refunded(@PathVariable("orderId")String orderId){
        PaymentRefund refund=paymentService.refundedPayment(orderId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/validate/{transactionId}")
    public ResponseEntity<Boolean> validatePayment(@PathVariable String transactionId) {
        boolean valid = paymentService.validatePayment(transactionId);
        return ResponseEntity.ok(valid);
    }
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}