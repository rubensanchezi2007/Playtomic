package com.playtomic.tests.wallet.exception;

import com.playtomic.tests.wallet.model.WalletError;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class WalletException extends RuntimeException{

    private final WalletError error;
    private final HttpStatus httpStatus;


    public static WalletException notFound(WalletError walletError)
    {
        return WalletException.builder().error(walletError).httpStatus(HttpStatus.OK).build();
    }

}
