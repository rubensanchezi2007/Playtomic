package com.playtomic.tests.wallet.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DepositError {

    public static final DepositError ERROR_PROCESSING_DEPOSIT_GATEWAY =
            of(101,"Error processing deposit through Gateway");

    private final int errorCode;
    private final String errorMessage;

    public static DepositError of (int errorCode, String errorMessage)
    {
        return new DepositError(errorCode,errorMessage);
    }
}
