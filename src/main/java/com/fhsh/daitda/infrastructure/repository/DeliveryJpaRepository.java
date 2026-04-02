package com.fhsh.daitda.infrastructure.repository;

import com.fhsh.daitda.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID> {
}
