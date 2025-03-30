package com.playtomic.tests.wallet.exception;

import com.playtomic.tests.wallet.model.DepositError;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice (annotations = RestController.class)
public class WalletControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<WalletError> walletError(final WalletException e)
    {
        return new ResponseEntity<>(
                WalletError.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build(),
                e.getHttpStatus()
        );
    }
}
