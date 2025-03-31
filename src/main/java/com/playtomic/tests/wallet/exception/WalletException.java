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


    public static WalletException notFound()
    {
        return WalletException.builder().error(WalletError.WALLET_NOT_FOUND).httpStatus(HttpStatus.OK).build();
    }

}
