package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.model.dto.SignUpRequest;
import io.github.aleknik.scientificcenterservice.model.dto.SignUpResponse;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${payment-service-url}")
    private String url;

    public PaymentService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    public User register(User user) {

        final SignUpRequest signUpRequest = new SignUpRequest(user.getEmail());
        final SignUpResponse signUpResponse = restTemplate.postForObject(url + "/auth/signup", signUpRequest, SignUpResponse.class);

        user.setPaymentServiceId(signUpResponse.getClientId());
        return userRepository.save(user);
    }

}
