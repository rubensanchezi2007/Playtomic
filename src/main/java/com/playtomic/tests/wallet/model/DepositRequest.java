package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = DepositRequest.Builder.class)
public class DepositRequest {
    //Used for idempotency since im using POST
    private final Long requestId;
    private final String cardNumber;
    private final BigDecimal amount;
    private final Long walletId;



    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
