package com.arka.payments.mapper;

import com.arka.payments.models.Payment;
import com.arka.payments.resources.PaymentAmount;
import com.arka.payments.resources.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment requestToDomain(PaymentRequest request){
        Payment payment=new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());

        return payment;
    }
    public void updatePaymentDetails(Payment payment,PaymentAmount paymentAmount){

        payment.setCurrency(paymentAmount.currency());
        payment.setPaymentType(paymentAmount.paymentType());

    }
    public void updateUserDetails(Payment payment,PaymentAmount paymentAmount){

        payment.setUserId(paymentAmount.userId());
        payment.setUserEmail(paymentAmount.userEmail());

    }

}
