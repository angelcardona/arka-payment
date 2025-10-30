package com.arka.payments.services;


import com.arka.payments.feign.OrderClient;
import com.arka.payments.mapper.PaymentMapper;
import com.arka.payments.models.Payment;
import com.arka.payments.models.PaymentStatus;
import com.arka.payments.repository.PaymentJpaRepository;
import com.arka.payments.resources.PaymentAmount;
import com.arka.payments.resources.PaymentRefund;
import com.arka.payments.resources.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final static Logger log=Logger.getLogger(PaymentService.class.getName());
    private final PaymentJpaRepository repository;
    private final PaymentMapper mapper;
    private final OrderClient orderClient;


    public String processPayment(PaymentRequest request){
        Payment payment=mapper.requestToDomain(request);
        Payment saved = repository.save(payment);
        return saved.getId();
    }
    public String acceptPayment(String transactionId,PaymentAmount amount){
        Payment payment =repository.findById(transactionId)
                .orElseThrow(()-> new IllegalArgumentException("Payment not Found by Id"));
        Double expectPayment=expectPaymentTotal(payment.getId());
        if (!expectPayment.equals(amount.amount())){
            payment.switchToRejected();
            repository.save(payment);
            throw new IllegalArgumentException("amount must be valid");
        }
        mapper.updatePaymentDetails(payment,amount);
        payment.switchToAccepted();
        payment.setUserId(amount.userId());
        orderClient.acceptOrder(payment.getOrderId());
        Payment saved=repository.save(payment);
        return saved.getId();
    }
    public void cancelPayment(String transactionId){
        Payment payment =repository.findById(transactionId)
                .orElseThrow(()-> new IllegalArgumentException("Payment not Found by Id"));
        payment.switchToCanceled();
        repository.save(payment);
    }
    public Boolean validatePayment(String transactionId){
        return repository.findById(transactionId)
                .map(p -> p.getStatus() == PaymentStatus.APPROVED)
                .orElse(false);
    }
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }
    private Double expectPaymentTotal(String transactionId){
        Payment payment =repository.findById(transactionId)
                .orElseThrow(()-> new IllegalArgumentException("Payment not Found by Id"));
        return  payment.getAmount();
    }
    public PaymentRefund refundedPayment(String orderId){
        Payment payment=repository.getByOrderId(orderId);
        if (!payment.getStatus().equals(PaymentStatus.APPROVED)){
            throw new IllegalArgumentException("For Refunded must be the payment approved");
        }
        payment.switchToRefunded();
        return new  PaymentRefund(payment.getUserId(),payment.getAmount());

    }

}
