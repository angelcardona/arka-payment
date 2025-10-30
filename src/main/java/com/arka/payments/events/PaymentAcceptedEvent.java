package com.arka.payments.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Getter
public class PaymentAcceptedEvent {

    private String id= UUID.randomUUID().toString();
    private String userId;
    private String userEmail;

    public PaymentAcceptedEvent(String userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
