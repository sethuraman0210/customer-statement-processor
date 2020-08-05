package com.rabobank.customer.controller.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.utils.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatementProcessContollerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    @Order(value = 1)
    @DisplayName("Test successful records scenario")
    public void testSuccessFulRecords() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/customer/statement/processor";
        URI uri = new URI(baseUrl);

        List<RecordDTO> recordDTOS = readJson("successRecords.json");

        HttpEntity<List> request = new HttpEntity<>(recordDTOS, null);

        ResponseEntity<ValidationResponseDTO> result = this.restTemplate.postForEntity(uri, request, ValidationResponseDTO.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(Constants.SUCCESSFUL, result.getBody().getResult());

    }


    @Test
    @Order(value = 2)
    @DisplayName("Test Duplicate record scenario")
    public void testDuplicateRecords() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/customer/statement/processor";
        URI uri = new URI(baseUrl);
        List<RecordDTO> recordDTOS = readJson("duplicateRecords.json");
        HttpHeaders headers = new HttpHeaders();


        HttpEntity<List> request = new HttpEntity<>(recordDTOS, null);

        ResponseEntity<ValidationResponseDTO> result = this.restTemplate.postForEntity(uri, request, ValidationResponseDTO.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(Constants.DUPLICATE_REFERENCE, result.getBody().getResult());

    }

    @Test
    @Order(value = 3)
    @DisplayName("Test EndBalance error record scenario")
    public void testEndBalanceErrorRecords() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/customer/statement/processor";
        URI uri = new URI(baseUrl);

        List<RecordDTO> recordDTOS = readJson("endBalanceErrorRecords.json");

        HttpEntity<List> request = new HttpEntity<>(recordDTOS, null);

        ResponseEntity<ValidationResponseDTO> result = this.restTemplate.postForEntity(uri, request, ValidationResponseDTO.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(Constants.INCORRECT_END_BALANCE, result.getBody().getResult());

    }

    @Test
    @Order(value = 4)
    @DisplayName("Test Duplicate & EndBalance error record scenario")
    public void testDuplicateEndBalanceErrorRecords() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/customer/statement/processor";
        URI uri = new URI(baseUrl);

        List<RecordDTO> recordDTOS = readJson("duplicateEndBalanceRecords.json");

        HttpEntity<List> request = new HttpEntity<>(recordDTOS, null);

        ResponseEntity<ValidationResponseDTO> result = this.restTemplate.postForEntity(uri, request, ValidationResponseDTO.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, result.getBody().getResult());

    }

    @Test
    @Order(value = 5)
    @DisplayName("Test BadRequest scenario")
    public void testBadRequest() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/customer/statement/processor";
        URI uri = new URI(baseUrl);

        List<RecordDTO> recordDTOS = readJson("badRequest.json");

        HttpEntity<List> request = new HttpEntity<>(recordDTOS, null);

        ResponseEntity<ValidationResponseDTO> result = this.restTemplate.postForEntity(uri, request, ValidationResponseDTO.class);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertEquals(Constants.BAD_REQUEST, result.getBody().getResult());

    }

    private static List<RecordDTO> readJson(String recordFileName) {
        List<RecordDTO> records = null;
        try {
            ObjectMapper mapper = new ObjectMapper();

            records = mapper.readValue(Paths.get("src", "test", "resources", recordFileName).toFile(), new TypeReference<List<RecordDTO>>() {
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;

    }
}