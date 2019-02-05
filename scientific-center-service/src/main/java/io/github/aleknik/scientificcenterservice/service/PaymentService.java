package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.User;
import io.github.aleknik.scientificcenterservice.model.dto.*;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PaperRepository paperRepository;

    @Value("${payment-service-url}")
    private String url;

    public PaymentService(RestTemplate restTemplate, UserRepository userRepository, PaperRepository paperRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
    }

    public User register(User user) {

        try {
            final SignUpRequest signUpRequest = new SignUpRequest(user.getEmail());
            final SignUpResponse signUpResponse = restTemplate.postForObject(url + "/auth/signup", signUpRequest, SignUpResponse.class);

            user.setPaymentServiceId(signUpResponse.getClientId());
        } catch (RestClientException ignored) {
        }
        return userRepository.save(user);
    }

    public List<RegisteredMethodResponse> getRegisterMethods(User user) {
        final String clientId = user.getPaymentServiceId();
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/auth/registered-methods")
                .queryParam("clientId", clientId);

        final RegisteredMethodResponse[] response = restTemplate.getForObject(builder.build().encode().toUri(), RegisteredMethodResponse[].class);

        return Arrays.asList(response);
    }

    public String buyPaper(long id, User user, String successUrl, String errorUrl) {
        final Paper paper = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));

        final PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProductId(String.valueOf(id));
        paymentRequest.setBuyerId(String.valueOf(user.getId()));
        paymentRequest.setAmount(paper.getJournal().getPaperPrice());
        paymentRequest.setClientId(paper.getJournal().getEditor().getPaymentServiceId());
        paymentRequest.setSubscriptionBased(false);
        paymentRequest.setSuccessUrl(successUrl);
        paymentRequest.setErrorUrl(errorUrl);

        return restTemplate.postForObject(url + "/payments", paymentRequest, String.class);
    }

    public PaymentStatus paperStatus(long id, User user) {
        final Paper paper = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));

        final PaymentQuery paymentQuery = new PaymentQuery();
        paymentQuery.setBuyerId(String.valueOf(user.getId()));
        paymentQuery.setClientId(paper.getJournal().getEditor().getPaymentServiceId());
        paymentQuery.setProductId(String.valueOf(paper.getId()));

        try {
            return restTemplate.postForObject(url + "/payments/status", paymentQuery, PaymentStatusResponse.class)
                    .getPaymentStatus();
        } catch (RestClientException e) {
            throw new NotFoundException("payment not found");
        }
    }
}
