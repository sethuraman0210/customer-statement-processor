package com.rabobank.customer.dto.response;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * ValidationResponseDTO holds the final result of statement processing and set of error records
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ValidationResponseDTO {

    /**
     * Result represents the final status of the statement processing
     */
    private String result;


    /**
     * Set of errorRecords holds the details of the records which has error
     */
    private Set<ErrorRecordDTO> errorRecords;


    /**
     * Constructor to initialize the ValidationResponseDTO obj
     *
     * @param result
     */
    public ValidationResponseDTO(String result) {
        this.result = result;
        this.errorRecords = new HashSet<>();
    }
}
