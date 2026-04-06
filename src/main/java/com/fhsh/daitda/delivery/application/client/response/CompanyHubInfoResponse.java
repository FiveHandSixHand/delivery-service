package com.fhsh.daitda.delivery.application.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyHubInfoResponse {
    private UUID companyId;
    private UUID hubId;
    private String name;
    private String type;
    private AddressResponse address;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressResponse {
        private String city;
        private String district;
        private String street;
    }
    public String getFullAddress() {
        return address.getCity() + " " + address.getDistrict() + " " + address.getStreet();
    }
}
