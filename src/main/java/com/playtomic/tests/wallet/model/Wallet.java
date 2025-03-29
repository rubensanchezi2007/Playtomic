package com.playtomic.tests.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Wallet")
public class Wallet {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    @ColumnDefault("0")
    private BigDecimal balance;


    @Column
    private String currency;


    @OneToMany (mappedBy = "wallet")
    List<Deposit> depositList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="player_id")
    Player player;

}
