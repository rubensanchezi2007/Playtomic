package com.playtomic.tests.wallet.exception;

import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.model.DepositError;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = WalletController.class)
public class WalletControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WalletControllerAdvice.class);


    @ExceptionHandler(WalletException.class)
    public ResponseEntity<WalletError> walletError(final WalletException e)
    {
        log.error("Wallet call failed due to errorCode {}, errorMessage {}",e.getError().getErrorCode(),e.getError().getErrorMessage());
        return new ResponseEntity<>(
                WalletError.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build(),
                e.getHttpStatus()
        );
    }
}
