package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletResponse {
    private final String playerEmailAddress;
    private final String name;
    private final BigDecimal balance;
    private final String currency;
    private final List<Deposit> depositList;


    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}
}
