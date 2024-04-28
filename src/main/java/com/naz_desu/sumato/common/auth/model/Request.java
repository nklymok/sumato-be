package com.naz_desu.sumato.common.auth.model;

public record Request(
        String ip,
        String language,
        String method,
        GeoIP geoip,
        String hostname,
        String userAgent
) {}
