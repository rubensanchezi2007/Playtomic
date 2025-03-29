package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.WalletResponse;

public interface IWalletService {

    WalletResponse getWalletByID (Long id);
}
