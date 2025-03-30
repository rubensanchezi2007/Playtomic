package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.Deposit;
import com.playtomic.tests.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository <Deposit,Long> {

    Optional<Deposit> findByRequestId(Long requestId);
}
