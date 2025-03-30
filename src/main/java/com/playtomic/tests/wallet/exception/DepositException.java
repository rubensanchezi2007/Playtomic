package com.playtomic.tests.wallet.exception;

import com.playtomic.tests.wallet.model.DepositError;
import com.playtomic.tests.wallet.model.WalletError;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class DepositException extends RuntimeException{

    private final DepositError error;
    private final HttpStatus httpStatus;


    public static DepositException notFound(DepositError depositError)
    {
        return DepositException.builder().error(depositError).httpStatus(HttpStatus.OK).build();
    }


    public static DepositException alreadyExists()
    {
        return DepositException.builder().error(DepositError.ERROR_PROCESSING_DEPOSIT_ALREADY_EXIST).httpStatus(HttpStatus.OK).build();
    }
}
