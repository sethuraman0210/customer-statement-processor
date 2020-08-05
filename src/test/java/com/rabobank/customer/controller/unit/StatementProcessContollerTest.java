package com.rabobank.customer.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.customer.controller.StatementProcessContoller;
import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.helper.TestingDataHelper;
import com.rabobank.customer.service.StatementProcessService;
import com.rabobank.customer.utils.Constants;
import com.rabobank.customer.validator.RequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration

public class StatementProcessContollerTest {

    private MockMvc mvc;

    @Mock
    StatementProcessService statementProcessService;

    @Mock
    RequestValidator recordsValidator;

    @InjectMocks
    StatementProcessContoller statementProcessContoller;

    private static List<RecordDTO> recordDTOS;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(statementProcessContoller).build();
        recordDTOS = TestingDataHelper.setRecordsforUnitTesting();
    }

    static {
        final ApplicationContext app = new AnnotationConfigApplicationContext(StatementProcessContollerTest.class);
    }

    @Test
    @Order(value = 1)
    @DisplayName("Unit testing for successful records scenario")
    public void unitTestSuccessFulRecords() throws Exception {

        when(statementProcessService.processStatementRecords(any())).thenReturn(new ValidationResponseDTO(Constants.SUCCESSFUL));

        mvc.perform(MockMvcRequestBuilders
                .post("/customer/statement/processor")
                .content(asJsonString(recordDTOS))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.SUCCESSFUL));
    }

    @Test
    @Order(value = 2)
    @DisplayName("Unit testing for Duplicate record scenario")
    public void unitTestDuplicateRecords() throws Exception {

        RecordDTO record = new RecordDTO();
        record.setReference(BigDecimal.valueOf(194261));
        record.setAccountNumber("NL91RABO0315273637");
        record.setDescription("Clothes from Jan Bakker");
        record.setEndBalance(BigDecimal.valueOf(21.6));
        record.setStartBalance(BigDecimal.TEN);
        record.setMutation(BigDecimal.valueOf(31.6));

        recordDTOS.add(record);

        when(statementProcessService.processStatementRecords(any())).thenReturn(new ValidationResponseDTO(Constants.DUPLICATE_REFERENCE));

        mvc.perform(MockMvcRequestBuilders
                .post("/customer/statement/processor")
                .content(asJsonString(recordDTOS))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.DUPLICATE_REFERENCE));
    }

    @Test
    @Order(value = 3)
    @DisplayName("Unit testing for EndBalance error record scenario")
    public void unitTestEndBalanceErrorRecords() throws Exception {

        RecordDTO record = new RecordDTO();
        record.setReference(BigDecimal.valueOf(194261));
        record.setAccountNumber("NL91RABO0315273637");
        record.setDescription("Clothes from Jan Bakker");
        record.setEndBalance(BigDecimal.valueOf(21.6));
        record.setStartBalance(BigDecimal.TEN);
        record.setMutation(BigDecimal.valueOf(30.6));

        recordDTOS.add(record);

        when(statementProcessService.processStatementRecords(any())).thenReturn(new ValidationResponseDTO(Constants.INCORRECT_END_BALANCE));

        mvc.perform(MockMvcRequestBuilders
                .post("/customer/statement/processor")
                .content(asJsonString(recordDTOS))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.INCORRECT_END_BALANCE));
    }


    @Test
    @Order(value = 4)
    @DisplayName("Unit testing for Duplicate & EndBalance error record scenario")
    public void unitTestDuplicateEndBalanceErrorRecords() throws Exception {

        RecordDTO record1 = new RecordDTO();
        record1.setReference(BigDecimal.valueOf(194261));
        record1.setAccountNumber("NL91RABO0315273637");
        record1.setDescription("Clothes from Jan Bakker");
        record1.setStartBalance(BigDecimal.TEN);
        record1.setMutation(BigDecimal.valueOf(30.6));
        record1.setEndBalance(BigDecimal.valueOf(21.6));
        recordDTOS.add(record1);

        RecordDTO record2 = new RecordDTO();
        record2.setReference(BigDecimal.valueOf(1942645));
        record2.setAccountNumber("NL91RABO0315273667");
        record2.setDescription("Clothes from John Miller");
        record2.setStartBalance(BigDecimal.TEN);
        record2.setMutation(BigDecimal.valueOf(1.6));
        record2.setEndBalance(BigDecimal.valueOf(21.6));

        recordDTOS.add(record2);

        when(statementProcessService.processStatementRecords(any())).thenReturn(new ValidationResponseDTO(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE));

        mvc.perform(MockMvcRequestBuilders
                .post("/customer/statement/processor")
                .content(asJsonString(recordDTOS))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}