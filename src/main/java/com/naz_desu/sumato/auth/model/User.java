package com.naz_desu.sumato.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record User(
       String tenant,
       String username,
       String email,
       @JsonProperty("phoneNumber")
       String phoneNumber,
       String userId,
       Instant createdAt,
       boolean emailVerified,
       String familyName,
       String givenName,
       Instant lastPasswordReset,
       String name,
       String nickname,
       String phoneVerified,
       String picture,
       Instant updatedAt,
       Map<String, Object> appMetadata,
       Map<String, Object> userMetadata
) {
}
