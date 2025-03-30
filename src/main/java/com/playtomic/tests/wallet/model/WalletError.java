package com.playtomic.tests.wallet.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class WalletError {

    public static final WalletError WALLET_NOT_FOUND =
            of(101,"Wallet not found");

    private final int errorCode;
    private final String errorMessage;

    public static WalletError of (int errorCode, String errorMessage)
    {
        return new WalletError(errorCode,errorMessage);
    }
}
