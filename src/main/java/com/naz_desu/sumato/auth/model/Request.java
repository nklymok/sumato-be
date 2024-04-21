package com.naz_desu.sumato.auth.model;

public record Request(
        String ip,
        String language,
        String method,
        GeoIP geoip,
        String hostname,
        String userAgent
) {}
