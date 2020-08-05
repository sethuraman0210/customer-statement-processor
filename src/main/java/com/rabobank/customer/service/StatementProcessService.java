package com.rabobank.customer.service;

import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;

import java.util.List;

/**
 * Interface StatementProcessService
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

public interface StatementProcessService {

    ValidationResponseDTO processStatementRecords(List<RecordDTO> records);
}
