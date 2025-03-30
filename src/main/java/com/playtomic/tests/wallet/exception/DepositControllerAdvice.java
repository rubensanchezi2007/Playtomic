package com.playtomic.tests.wallet.exception;

import com.playtomic.tests.wallet.model.DepositError;
import com.playtomic.tests.wallet.model.WalletError;
import com.playtomic.tests.wallet.service.StripeRestTemplateResponseErrorHandler;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice (annotations = RestController.class)
public class DepositControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StripeServiceException.class)
    public ResponseEntity<DepositError> depositStripeError(final StripeServiceException e)
    {
        return new ResponseEntity<>(
                DepositError.builder()
                        .errorCode(DepositError.ERROR_PROCESSING_DEPOSIT_GATEWAY.getErrorCode())
                        .errorMessage(DepositError.ERROR_PROCESSING_DEPOSIT_GATEWAY.getErrorMessage())
                        .build(),
                HttpStatus.OK
        );

    }

    @ExceptionHandler(DepositException.class)
    public ResponseEntity<DepositError> depositError(final DepositException e)
    {
        return new ResponseEntity<>(
                DepositError.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build(),
                HttpStatus.OK
        );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DepositError> depositGeneralError(final Exception e) {
        return new ResponseEntity<>(
                DepositError.builder()
                        .errorCode(DepositError.ERROR_PROCESSING_DEPOSIT_GATEWAY.getErrorCode())
                        .errorMessage(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
