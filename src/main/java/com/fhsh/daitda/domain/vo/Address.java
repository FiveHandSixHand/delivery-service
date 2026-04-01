package com.fhsh.daitda.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeExclude;

@Embeddable
@Getter
@EqualsAndHashCode // 값이 같으면 같은 객체로 취급하게 해주는 annotation
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String city;
    private String street;
    private String zipCode;

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public static Address of(String city, String street, String zipCode) {
        return new Address(city, street, zipCode);
    }
}
