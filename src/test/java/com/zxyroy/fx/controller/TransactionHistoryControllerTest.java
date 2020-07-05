package com.zxyroy.fx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxyroy.fx.domain.DTO.TransactionHistoryDTO;
import com.zxyroy.fx.domain.DTO.TransactionHistoryListDTO;
import com.zxyroy.fx.domain.request.TransactionHistoryRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionHistoryControllerTest {
    private final String baseUri = "/api/transaction/history";

    @Autowired
    private TestRestTemplate restTemplate;

    private TransactionHistoryRequest transactionHistoryRequestValid;
    private TransactionHistoryRequest transactionHistoryRequestInvalidRate;
    private TransactionHistoryRequest transactionHistoryRequestInvalidUserId;
    private TransactionHistoryRequest transactionHistoryRequestInvalidAmount;

    @SneakyThrows
    @PostConstruct
    private void init() {
        ObjectMapper mapper = new ObjectMapper();
        URL transactionHistoryRequestValidJson = TransactionHistoryControllerTest.class.getClassLoader().getResource("data/transactionRequest.json");
        transactionHistoryRequestValid = mapper.readValue(Path.of(transactionHistoryRequestValidJson.toURI()).toFile(), TransactionHistoryRequest.class);
        URL transactionHistoryRequestInValidRateJson = TransactionHistoryControllerTest.class.getClassLoader().getResource("data/transactionRequestInvalidRate.json");
        transactionHistoryRequestInvalidRate = mapper.readValue(Path.of(transactionHistoryRequestInValidRateJson.toURI()).toFile(), TransactionHistoryRequest.class);
        URL transactionHistoryRequestInvalidUserIdJson = TransactionHistoryControllerTest.class.getClassLoader().getResource("data/transactionRequestInvalidUserId.json");
        transactionHistoryRequestInvalidUserId = mapper.readValue(Path.of(transactionHistoryRequestInvalidUserIdJson.toURI()).toFile(), TransactionHistoryRequest.class);
        URL transactionHistoryRequestInvalidAmountJson = TransactionHistoryControllerTest.class.getClassLoader().getResource("data/transactionRequestInvalidAmount.json");
        transactionHistoryRequestInvalidAmount = mapper.readValue(Path.of(transactionHistoryRequestInvalidAmountJson.toURI()).toFile(), TransactionHistoryRequest.class);
    }

    @Test
    @Order(1)
    void saveRequestSuccess() {
        ResponseEntity<TransactionHistoryDTO> response = restTemplate.postForEntity(baseUri, transactionHistoryRequestValid, TransactionHistoryDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, response.getBody().getId());
    }

    @Test
    @Order(2)
    void saveRequestInvalidRate() {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri, transactionHistoryRequestInvalidRate, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    void saveRequestInvalidUserId() {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri, transactionHistoryRequestInvalidUserId, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(4)
    void saveRequestInvalidAmount() {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri, transactionHistoryRequestInvalidAmount, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    void getRequestSuccess() {
        ResponseEntity<TransactionHistoryListDTO> response = restTemplate.getForEntity(baseUri + "?page=0&size=100", TransactionHistoryListDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, response.getBody().getCount());
        Assertions.assertEquals(1, response.getBody().getTransactionHistoryList().size());
    }

    @Test
    @Order(6)
    void getRequestSuccess2() {
        ResponseEntity<TransactionHistoryListDTO> response = restTemplate.getForEntity(baseUri + "?page=1&size=100", TransactionHistoryListDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, response.getBody().getCount());
        Assertions.assertEquals(0, response.getBody().getTransactionHistoryList().size());
    }

    @Test
    @Order(7)
    void getRequestInvalidPage() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUri + "?page=-1&size=100", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    void getRequestInvalidSizeLessThan0() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUri + "?page=0&size=0", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(9)
    void getRequestInvalidSizeMoreThan1000() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUri + "?page=0&size=1001", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}