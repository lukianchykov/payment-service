package com.iprody.paymentserviceapp.service;

import java.util.List;

import com.iprody.paymentserviceapp.persistence.PaymentFilter;
import com.iprody.paymentserviceapp.persistence.PaymentFilterFactory;
import com.iprody.paymentserviceapp.persistence.PaymentRepository;
import com.iprody.paymentserviceapp.persistence.entity.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> search(PaymentFilter filter) {
        Specification<Payment> spec =
            PaymentFilterFactory.fromFilter(filter);
        return paymentRepository.findAll(spec);
    }

    public Page<Payment> searchPaged(PaymentFilter filter, Pageable
        pageable) {
        Specification<Payment> spec =
            PaymentFilterFactory.fromFilter(filter);
        return paymentRepository.findAll(spec, pageable);
    }
}