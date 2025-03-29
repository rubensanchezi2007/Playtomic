package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder(builderClassName = "Builder", toBuilder = true)
public class WalletResponse {
    private final String playerEmailAddress;
    private final String name;
    private final BigDecimal balance;
    private final String currency;


    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
