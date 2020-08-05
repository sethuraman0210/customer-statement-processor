package com.rabobank.customer.service;

import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.helper.TestingDataHelper;
import com.rabobank.customer.service.impl.StatementProcessServiceImpl;
import com.rabobank.customer.utils.Constants;
import com.rabobank.customer.validator.RecordsBusinessValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatementProcessServiceImplTest {
    @Mock
    RecordsBusinessValidator businessValidator;

    @InjectMocks
    StatementProcessServiceImpl statementProcessService;

    private static List<RecordDTO> recordDTOS;

    @Before
    public void setUp() {
        recordDTOS = TestingDataHelper.setRecordsforUnitTesting();
    }

    @Test
    @Order(value = 1)
    @DisplayName("Test Statement processing of Records")
    public void processStatement() {

        when(businessValidator.validate(any())).thenReturn(new ValidationResponseDTO(Constants.SUCCESSFUL));

        ValidationResponseDTO result = statementProcessService.processStatementRecords(recordDTOS);

        Assert.assertEquals(result.getResult(), Constants.SUCCESSFUL);

    }
}
