package io.github.aleknik.scientificcenterservice.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class RestTemplateConfig {

    @Value("${COIN10_MERCHANT_KEY_PASS}")
    private String pass;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
        char[] password = pass.toCharArray();

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore("classpath:merchantKeyStore.p12", password), password)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

        return builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }

    private KeyStore keyStore(String file, char[] password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        File key = ResourceUtils.getFile(file);
        try (InputStream in = new FileInputStream(key)) {
            keyStore.load(in, password);
        }
        return keyStore;
    }
}
