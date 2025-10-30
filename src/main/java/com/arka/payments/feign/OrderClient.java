package com.arka.payments.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service",url = "http://localhost:8080/orders")
public interface OrderClient {
    @PostMapping("/accept/{orderId}")
    public void acceptOrder(@PathVariable String orderId);
}
