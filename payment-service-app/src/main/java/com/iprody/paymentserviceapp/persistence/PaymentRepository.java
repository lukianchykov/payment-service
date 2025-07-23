package com.iprody.paymentserviceapp.persistence;

import java.util.UUID;

import com.iprody.paymentserviceapp.persistence.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {
}
