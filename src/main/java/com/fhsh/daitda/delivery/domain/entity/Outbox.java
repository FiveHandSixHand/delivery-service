package com.fhsh.daitda.delivery.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;
import com.fhsh.daitda.domain.BaseUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_outbox")
public class Outbox extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "text")
    @Convert(converter = UuidListConverter.class)
    List<UUID> hubManagers;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DeliveryOutBoxStatus status;

    @Column(nullable = false)
    String topic;

    @Column(nullable = false)
    int retryCount;

    @Column(nullable = false)
    int maxRetryCount;

    @Builder(access = AccessLevel.PRIVATE)
    private Outbox(List<UUID> hubManagers, String topic) {
        this.hubManagers = hubManagers;
        this.topic = topic;
        this.status = DeliveryOutBoxStatus.PENDING;
        this.retryCount = 0;
        this.maxRetryCount = 3;
    }

    public static Outbox create(List<UUID> hubManagers, String topic) {
        return Outbox.builder()
                .hubManagers(hubManagers)
                .topic(topic)
                .build();
    }

    public void increaseRetryCount() {
        this.retryCount++;
    }

    public void complete() {
        this.status = DeliveryOutBoxStatus.COMPLETED;
    }

    public void fail() {
        this.status = DeliveryOutBoxStatus.FAILED;
    }

    public boolean isExhausted() {
        return this.retryCount >= this.maxRetryCount;
    }

    @Converter
    private static class UuidListConverter implements AttributeConverter<List<UUID>, String> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(List<UUID> attribute) {
            try {
                // List<T> -> String
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("UUID 리스트 직렬화 실패", e);
            }
        }

        @Override
        public List<UUID> convertToEntityAttribute(String dbData) {
            try {
                // String → List<T>
                return objectMapper.readValue(dbData, new TypeReference<List<UUID>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException("UUID 리스트 역직렬화 실패", e);
            }
        }
    }
}

