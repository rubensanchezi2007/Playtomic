package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletResponse;
import com.playtomic.tests.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    @Override
    public WalletResponse getWalletByID(Long id) {
        Optional<Wallet> wallet=walletRepository.findById(id);
        if (wallet.isPresent())
            return WalletResponse.builder()
                    .name(wallet.get().getName())
                    .balance(wallet.get().getBalance())
                    .playerEmailAddress(wallet.get().getPlayer().getEmailAddress())
                    .depositList(wallet.get().getDepositList())
                    .build();
        else
            return WalletResponse.builder().build();

    }
}
