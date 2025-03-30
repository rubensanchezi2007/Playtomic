package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //IDempotency with DB
    private Long requestId;

    @Column
    BigDecimal amount;

    // To control the deposit lifecycle
    @Column
    String status;

    // Any useful information specially for declines
    @Column
    String note;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "wallet_id")
    Wallet wallet;

    @JsonPOJOBuilder(withPrefix="")
    public static class Builder {}


}
