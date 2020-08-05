package com.rabobank.customer.validator;


import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.helper.TestingDataHelper;
import com.rabobank.customer.utils.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class RecordsBusinessValidatorTest {

    @InjectMocks
    RecordsBusinessValidator recordsBusinessValidator;

    @Test
    @Order(value = 1)
    @DisplayName("Successful records scenario")
    public void validateSuccess() {

        List<RecordDTO> recordDTOS = TestingDataHelper.setRecordsforUnitTesting();

        ValidationResponseDTO result = recordsBusinessValidator.validate(recordDTOS);

        Assert.assertEquals(result.getResult(), Constants.SUCCESSFUL);

    }

    @Test
    @Order(value = 2)
    @DisplayName("Duplicate record scenario")
    public void validateDuplicateRecords() {

        List<RecordDTO> recordDTOS = TestingDataHelper.setRecordsforUnitTesting();

        RecordDTO record = new RecordDTO();
        record.setReference(BigDecimal.valueOf(194261));
        record.setAccountNumber("NL91RABO0315273456");
        record.setDescription("Clothes from Jan Bakker");
        record.setStartBalance(BigDecimal.valueOf(23.6));
        record.setMutation(BigDecimal.TEN);
        record.setEndBalance(BigDecimal.valueOf(33.6));

        recordDTOS.add(record);

        ValidationResponseDTO result = recordsBusinessValidator.validate(recordDTOS);

        Assert.assertEquals(result.getResult(), Constants.DUPLICATE_REFERENCE);

    }

    @Test
    @Order(value = 3)
    @DisplayName("EndBalance error record scenario")
    public void validateEndBalanceErrorRecords() {

        List<RecordDTO> recordDTOS = TestingDataHelper.setRecordsforUnitTesting();

        RecordDTO record = new RecordDTO();
        record.setReference(BigDecimal.valueOf(194264));
        record.setAccountNumber("NL91RABO0315273637");
        record.setDescription("Clothes from Jan Bakker");
        record.setStartBalance(BigDecimal.valueOf(21.6));
        record.setMutation(BigDecimal.TEN);
        record.setEndBalance(BigDecimal.valueOf(30.6));

        recordDTOS.add(record);

        ValidationResponseDTO result = recordsBusinessValidator.validate(recordDTOS);

        Assert.assertEquals(result.getResult(), Constants.INCORRECT_END_BALANCE);

    }

    @Test
    @Order(value = 4)
    @DisplayName("Duplicate & EndBalance error record scenario")
    public void validateDuplicateEndBalanceErrorRecords() {

        List<RecordDTO> recordDTOS = TestingDataHelper.setRecordsforUnitTesting();

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

        ValidationResponseDTO result = recordsBusinessValidator.validate(recordDTOS);

        Assert.assertEquals(result.getResult(), Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);

    }


}
