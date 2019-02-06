package io.github.aleknik.scientificcenterservice.service.util;

import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.dto.LocationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    @Value("${geocoding.url}")
    private String baseUrl;

    @Value("${geocoding.key}")
    private String key;

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Address getAddress(String city, String country) {


        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", key)
                .queryParam("format", "json")
                .queryParam("city", city)
                .queryParam("country", country);
        LocationDto response;
        try {
            response = restTemplate.getForObject(builder.build().encode().toUri(), LocationDto[].class)[0];
        } catch (RestClientException e) {
            response = new LocationDto();
            response.setLat("45.2551338");
            response.setLon("19.8451756");
        }


        final LocationDto locationDto = response;

        return new Address(city, country, Double.parseDouble(locationDto.getLon()),
                Double.parseDouble(locationDto.getLat()));
    }
}
