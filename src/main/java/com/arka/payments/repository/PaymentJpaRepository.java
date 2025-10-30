package com.arka.payments.repository;

import com.arka.payments.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment,String> {
    Payment getByOrderId(String orderId);
}
