package com.iprody.paymentserviceapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iprody.paymentserviceapp.model.Payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final Map<Long, Payment> paymentStorage = new HashMap<>();

    public PaymentController() {
        paymentStorage.put(1L, new Payment(1L, 99.99));
        paymentStorage.put(2L, new Payment(2L, 150.00));
        paymentStorage.put(3L, new Payment(3L, 75.25));
        paymentStorage.put(4L, new Payment(4L, 200.00));
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentStorage.get(id);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentStorage.values());
    }
}
