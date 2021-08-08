package com.rabobank.customer.service.impl;

import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.service.StatementProcessService;
import com.rabobank.customer.validator.RecordsBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service method StatementProcessServiceImpl is responsible for
 * business operations for processing the record statements
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Component
public class StatementProcessServiceImpl implements StatementProcessService {

    @Autowired
    RecordsBusinessValidator recordsBusinessValidator;

    /**
     * processStatementRecords method is
     *
     * responsible
     * doing a confilt scenario
     * for routing the records to the validator
     *
     * @param records
     * @return ValidationResponseDTO
     */
    @Override
    public ValidationResponseDTO processStatementRecords(List<RecordDTO> records) {



        return recordsBusinessValidator.validate(records);
    }


}
