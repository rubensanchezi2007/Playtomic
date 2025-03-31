package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletResponse;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface WalletRepository extends JpaRepository <Wallet,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Wallet findWalletById (Long walletId );
}
