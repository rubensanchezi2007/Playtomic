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
@JsonDeserialize(builder = DepositError.Builder.class)
@RequiredArgsConstructor
public class DepositError {

    public static final DepositError ERROR_PROCESSING_DEPOSIT_UNEXPECTED =
            of(202,"Unexpected Error processing deposit");



    public static final DepositError ERROR_PROCESSING_DEPOSIT_GATEWAY =
            of(202,"Error processing deposit through Gateway");

    public static final DepositError ERROR_PROCESSING_DEPOSIT_ALREADY_EXIST =
            of(203,"Duplicated Deposit. Do nothing");


    private final int errorCode;
    private final String errorMessage;

    public static DepositError of (int errorCode, String errorMessage)
    {
        return new DepositError(errorCode,errorMessage);
    }

    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
