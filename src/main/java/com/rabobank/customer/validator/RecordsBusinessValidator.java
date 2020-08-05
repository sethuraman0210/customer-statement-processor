package com.rabobank.customer.validator;

import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ErrorRecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RecordsBusinessValidator performs all the business validations on the request and
 * provides the response based on the validation
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Component
public class RecordsBusinessValidator {

    private static final Logger log = LoggerFactory.getLogger(RecordsBusinessValidator.class);

    /**
     * Method validates the List<RecordDTO> and returns the ValidationResponseDTO obj
     *
     * @param records
     * @return ValidationResponseDTO
     */
    public ValidationResponseDTO validate(List<RecordDTO> records) {

        log.info("Inside :: RecordsBusinessValidator :: validate :: validation of records");

        Set<ErrorRecordDTO> errorRecordset = new HashSet<>();
        boolean isDuplicateReference = false;
        boolean isInCorrectEndBalance = false;

        for (RecordDTO record : records) {
            boolean isErrorRecord = false;
            if (checkDuplicateReference(records, record)) {
                isErrorRecord = true;
                isDuplicateReference = true;
            }
            if (checkInCorrectEndBalance(record)) {
                isErrorRecord = true;
                isInCorrectEndBalance = true;
            }
            if (isErrorRecord) {
                ErrorRecordDTO errorRecord = new ErrorRecordDTO();
                errorRecord.setReference(record.getReference());
                errorRecord.setAccountNumber(record.getAccountNumber());
                errorRecordset.add(errorRecord);
            }
        }
        log.info("RecordsBusinessValidator :: validate :: isDuplicateReference :: {} :: isInCorrectEndBalance :: {}"
                , isDuplicateReference, isInCorrectEndBalance);

        return getProcessorResponseDTO(errorRecordset, isDuplicateReference, isInCorrectEndBalance);
    }

    /**
     * Method checks whether the EndBalance is the sum of StartBalance and Mutation
     *
     * @param record
     * @return boolean
     */
    private static boolean checkInCorrectEndBalance(RecordDTO record) {

        log.info("Inside :: RecordsBusinessValidator :: checkInCorrectEndBalance :: validation of endBalance");

        return !(record.getStartBalance().add(record.getMutation())).stripTrailingZeros().equals(record.getEndBalance().stripTrailingZeros());
    }

    /**
     * The method whether the record is duplicate input list of records
     *
     * @param records List of records
     * @param record  record to be validate
     * @return boolean status
     */
    private static boolean checkDuplicateReference(List<RecordDTO> records, RecordDTO record) {

        log.info("Inside :: RecordsBusinessValidator :: checkDuplicateReference :: validation of duplicate records");

        return Collections.frequency(records, record) > 1;
    }

    /**
     * Method sets the final result based on the isRecordsDuplicate and recordsBalanceError
     * in the ValidationResponseDTO obj
     *
     * @param errorRecordSet      Set of information of the error records
     * @param isRecordsDuplicate  Defines whether the input request has duplicate records
     * @param recordsBalanceError Defines whether the input request has endBalance error
     * @return ValidationResponseDTO Respsone for the customer statement processing
     */
    private static ValidationResponseDTO getProcessorResponseDTO(Set<ErrorRecordDTO> errorRecordSet, boolean isRecordsDuplicate, boolean recordsBalanceError) {

        log.info("Start :: RecordsBusinessValidator :: getProcessorResponseDTO :: Set final result for statement process");

        ValidationResponseDTO responseDTO = new ValidationResponseDTO();

        if (isRecordsDuplicate && recordsBalanceError) {
            responseDTO.setResult(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
        } else if (isRecordsDuplicate) {
            responseDTO.setResult(Constants.DUPLICATE_REFERENCE);
        } else if (recordsBalanceError) {
            responseDTO.setResult(Constants.INCORRECT_END_BALANCE);
        } else {
            responseDTO.setResult(Constants.SUCCESSFUL);
        }
        responseDTO.setErrorRecords(errorRecordSet);
        log.info("End :: RecordsBusinessValidator :: getProcessorResponseDTO :: final result :: {}", responseDTO.getResult());

        return responseDTO;
    }
}
