package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = WalletError.Builder.class)
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

    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
