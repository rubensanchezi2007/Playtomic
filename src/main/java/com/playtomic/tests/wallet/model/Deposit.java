package com.playtomic.tests.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    BigDecimal amount;

    // To control the deposit lifecycle
    @Column
    String status;

    // Any useful information specially for declines
    @Column
    String note;

    @ManyToOne
    @JoinColumn (name = "wallet_id")
    Wallet wallet;
}
