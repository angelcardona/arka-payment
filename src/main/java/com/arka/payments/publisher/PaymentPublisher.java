package com.arka.payments.publisher;

import com.arka.payments.events.PaymentAcceptedEvent;

public interface PaymentPublisher {
    void paymentAcceptedPublisher(PaymentAcceptedEvent event);
}
