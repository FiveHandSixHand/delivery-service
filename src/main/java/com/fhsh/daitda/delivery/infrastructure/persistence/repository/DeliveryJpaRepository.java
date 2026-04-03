package com.fhsh.daitda.delivery.infrastructure.persistence.repository;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID> {
    @Query("SELECT DISTINCT d FROM Delivery d JOIN FETCH d.deliveryRoutes r WHERE r.deliveryManagerId = :deliveryManagerId")
    Slice<Delivery> findByDeliveryManagerId(@Param("deliveryManagerId") UUID deliveryManagerId, Pageable pageable);
    @Query("SELECT d FROM Delivery d WHERE d.departureHubId = :hubId OR d.destinationHubId = :hubId")
    Slice<Delivery> findByHubId(@Param("hubId") UUID hubId, Pageable pageable);
}
