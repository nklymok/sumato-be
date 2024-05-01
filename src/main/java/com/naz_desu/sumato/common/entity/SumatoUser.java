package com.naz_desu.sumato.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sumato_user")
public class SumatoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authId;
    private String publicId;
    // add these fields:  registered_at, ip_address, ip_address_country
    private Instant registeredAt;
    private String ipAddress;
    // format: ISO 3166-1 alpha-2
    private String ipAddressCountry;
}
