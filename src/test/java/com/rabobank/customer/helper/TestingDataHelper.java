package com.rabobank.customer.helper;

import com.rabobank.customer.dto.request.RecordDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestingDataHelper {

    public static List<RecordDTO> setRecordsforUnitTesting() {

        List<RecordDTO> recordDTOS = new ArrayList<>();

        RecordDTO record1 = new RecordDTO();
        record1.setReference(new BigDecimal(194261));
        record1.setAccountNumber("NL91RABO0315273637");
        record1.setDescription("Clothes from Jan Bakker");
        record1.setStartBalance(BigDecimal.valueOf(31.6));
        record1.setMutation(BigDecimal.valueOf(-10));
        record1.setEndBalance(BigDecimal.valueOf(21.6));
        recordDTOS.add(record1);

        RecordDTO record2 = new RecordDTO();
        record2.setReference(new BigDecimal(112806));
        record2.setAccountNumber("NL27SNSB0917829871");
        record2.setDescription("Clothes for Willem Dekker");
        record2.setStartBalance(BigDecimal.valueOf(91.23));
        record2.setMutation(new BigDecimal("+15.57"));
        record2.setEndBalance(BigDecimal.valueOf(106.8));

        recordDTOS.add(record2);

        return recordDTOS;
    }
}
