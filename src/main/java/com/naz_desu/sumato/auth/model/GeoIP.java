package com.naz_desu.sumato.auth.model;

public record GeoIP(
        String cityName,
        String continentCode,
        String countryCode3,
        String countryCode,
        String countryName,
        double latitude,
        double longitude,
        String subdivisionCode,
        String subdivisionName,
        String timeZone
) {}
