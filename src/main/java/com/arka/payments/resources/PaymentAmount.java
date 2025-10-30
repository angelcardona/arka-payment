package com.arka.payments.resources;

public record PaymentAmount(String userId,
                            String userEmail,
                            Double amount,
                            String currency,
                            String paymentType) {
}
