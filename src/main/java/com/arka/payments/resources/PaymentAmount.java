package com.arka.payments.resources;

public record PaymentAmount(String userId,Double amount,String currency,String paymentType) {
}
