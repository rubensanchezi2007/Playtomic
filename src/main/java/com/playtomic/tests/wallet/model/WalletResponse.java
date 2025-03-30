package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = WalletResponse.Builder.class)

public class WalletResponse {
    private final String playerEmailAddress;
    private final String name;
    private final BigDecimal balance;
    private final String currency;
    @JsonProperty("deposits")
    private final List<Deposit> depositList;


    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
