package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.DepositRequest;
import com.playtomic.tests.wallet.model.DepositResponse;
import com.playtomic.tests.wallet.model.WalletResponse;

public interface IDepositService {

    DepositResponse processDeposit (DepositRequest depositRequest);
}
