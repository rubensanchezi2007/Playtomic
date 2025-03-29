package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository <Wallet,Long> {

}
