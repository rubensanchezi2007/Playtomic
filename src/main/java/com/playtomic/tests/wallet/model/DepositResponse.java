package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = DepositResponse.Builder.class)

public class DepositResponse {
    private final String transactionId;
    private final BigDecimal amount;
    private final String currency;
    private final String status;


    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
