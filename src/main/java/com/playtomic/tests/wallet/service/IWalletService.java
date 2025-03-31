package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletResponse;

import java.math.BigDecimal;

public interface IWalletService {

    Wallet getWalletByID (Long id);



    void updateBalance (long id, BigDecimal amount,String paymentId,Long requestId);
}
