package com.rabobank.customer.controller;






import com.rabobank.customer.dto.request.RecordDTO;
import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.exception.StatementValidationException;
import com.rabobank.customer.service.StatementProcessService;
import com.rabobank.customer.validator.RequestValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Note:  that this REST EndPoint is Supposed to be consumed by the Rabo Bank
 * <p>
 * Customer Statement Processor REST service is not Accessible to the general Public.
 * </p>
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/customer")
public class StatementProcessContoller {

    private final Logger log = LoggerFactory.getLogger(StatementProcessContoller.class);

    @Autowired
    RequestValidator recordsValidator;

    @Autowired
    StatementProcessService statementProcessService;

    /**
     * The REST API post method "processStatementValidation" gets the List<RecordDTO>
     * as input to process the process the customer statement
     *
     * @param records
     * @param bindingResult
     * @return ResponseEntity<ValidationResponseDTO>
     * @throws StatementValidationException
     */

    @ApiOperation(value = "Customer statement records validaiton", notes = "", response = Void.class,
            tags = {"statementProcessor",})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Customer statement records Validated",
            response = ValidationResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ValidationResponseDTO.class)})

    @PostMapping(value = "/statement/processor", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ValidationResponseDTO> processStatementValidation(
            @ApiParam(value = "Monthly deliveries of customer statement records.", required = true)
            @Validated @RequestBody List<RecordDTO> records,
            BindingResult bindingResult) throws StatementValidationException {

        log.info("Request recieved for processing the Customer statement :: {}", records);

        recordsValidator.validate(records, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new StatementValidationException();
        } else {

            ValidationResponseDTO responseDTO = statementProcessService.processStatementRecords(records);

            log.info("Final response of processing the Customer statement :: {}", responseDTO);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }
    }
}
