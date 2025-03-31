package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.DepositProcessedEvent;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletError;
import com.playtomic.tests.wallet.model.WalletResponse;
import com.playtomic.tests.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);


    WalletRepository walletRepository;
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    public WalletService(WalletRepository walletRepository,ApplicationEventPublisher applicationEventPublisher) {
        this.walletRepository = walletRepository;
        this.applicationEventPublisher=applicationEventPublisher;
    }


    @Override
    public Wallet getWalletByID(Long id) {
        Optional<Wallet> wallet=walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        else {
            throw WalletException.notFound();
        }
    }

    @Transactional
    @Override
    public void updateBalance(long id, BigDecimal amount, String paymentId,Long requestId) {

        Wallet wallet =walletRepository.findWalletById(id);
        log.info("Updating balance currentBalance {} walletId {} amount {} paymentId {}",wallet.getBalance(),id,amount,paymentId);

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet );
        applicationEventPublisher.publishEvent(new DepositProcessedEvent(paymentId,requestId));
        log.info("Updated balance new balance {}",wallet.getBalance());

    }



}
