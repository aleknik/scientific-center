package io.github.aleknik.scientificcenterservice.controller;

import io.github.aleknik.scientificcenterservice.model.dto.PaymentRequest;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESPaperRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

@RestController
@RequestMapping("api/test")
public class TestController {

    private final ESPaperRepository ESPaperRepository;

    private final RestTemplate restTemplate;

    @Value("${payment-service-url}")
    private String url;

    public TestController(ESPaperRepository ESPaperRepository, RestTemplate restTemplate) {
        this.ESPaperRepository = ESPaperRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity test() {
        final PaperIndexUnit paperIndexUnit = new PaperIndexUnit();
        paperIndexUnit.setTitle("test");
        paperIndexUnit.setContent("sadasas");
        paperIndexUnit.setExternalId("1");
        paperIndexUnit.setJournal("dsfsd");
        paperIndexUnit.setKeywords(Arrays.asList("Earth", "Mars", "Venus"));
        paperIndexUnit.setOpenAccess(true);
//        paperIndexUnit.setReviewers(Arrays.asList(new Reviewer("2", "sada", "sdad")));

        final PaperIndexUnit index = ESPaperRepository.index(paperIndexUnit);
        return ResponseEntity.ok(index);
    }

    @GetMapping("/ssl")
    public ResponseEntity test1() {
        final PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(new BigDecimal("10"));
        paymentRequest.setBuyerId("1");
        paymentRequest.setClientId("1");
        paymentRequest.setProductId("1");
        paymentRequest.setErrorUrl("http://localhost:4200/error");
        paymentRequest.setSuccessUrl("http://localhost:4200/success");

        final String s = restTemplate.postForObject(url + "/payments", paymentRequest, String.class);

        return ResponseEntity.ok(s);
    }
}
