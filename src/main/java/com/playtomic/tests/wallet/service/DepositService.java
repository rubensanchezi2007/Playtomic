package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.api.DepositController;
import com.playtomic.tests.wallet.exception.DepositException;
import com.playtomic.tests.wallet.model.*;
import com.playtomic.tests.wallet.repository.DepositRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.github.benmanes.caffeine.cache.Cache;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Service
public class DepositService implements IDepositService {

    private static final Logger log = LoggerFactory.getLogger(DepositService.class);


    private final DepositRepository depositRepository;
    private final StripeService stripeService;
    private final WalletService walletService;
    private final CacheManager cacheManager;
    private final CacheService cacheService;

    @Autowired
    public DepositService(DepositRepository depositRepository, StripeService stripeService, WalletService walletService, CacheManager cacheManager, CacheService cacheService) {
        this.depositRepository = depositRepository;
        this.stripeService = stripeService;
        this.walletService = walletService;
        this.cacheManager = cacheManager;
        this.cacheService = cacheService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void refundDeposit(DepositProcessedEvent depositProcessedEvent) {
        log.info("Received event to refund deposit, paymentId {}",depositProcessedEvent.getPaymentId());
        stripeService.refund(depositProcessedEvent.getPaymentId());
        Deposit deposit= depositRepository.findByRequestId(depositProcessedEvent.getRequestId()).get();
        deposit.setStatus("DECLINED");
        deposit.setNote("Declined due to update balance rollback. Refunded.");
        depositRepository.save(deposit);

    }

    @Override
    public DepositResponse processDeposit(DepositRequest depositRequest) {

        //Cache mechanism for idempotency based on Cache and Caffeine. Not currently in use
       // Cache cache=(Cache) cacheManager.getCache("depositRequest").getNativeCache();
       //if (!cache.asMap().containsValue(depositRequest.getRequestId()))
        if (depositRepository.findByRequestId(depositRequest.getRequestId()).isEmpty())
        {
            cacheService.cacheRequestId(depositRequest);
            log.info("New deposit detected requestId {}",depositRequest.getRequestId());

            Wallet wallet= walletService.getWalletByID(depositRequest.getWalletId());

            Deposit deposit= new Deposit();
            deposit.setAmount(depositRequest.getAmount());
            deposit.setRequestId(depositRequest.getRequestId());
            deposit.setStatus("PENDING");
            deposit.setNote("Deposit created in pending status");
            deposit.setWallet(wallet);
            depositRepository.save(deposit);

            Payment payment=stripeService.charge(depositRequest.getCardNumber(),depositRequest.getAmount());

            deposit.setExternalPaymentId(payment.getId());
            deposit.setStatus("APPROVED");
            deposit.setNote("Deposit approved");
            depositRepository.save(deposit);

            walletService.updateBalance(wallet.getId(),depositRequest.getAmount(), payment.getId(), deposit.getRequestId());

            return DepositResponse.builder().amount(depositRequest.getAmount())
                    .currency(wallet.getCurrency())
                    .status(deposit.getStatus())
                    .transactionId(payment.getId()).build();
        }

        throw DepositException.alreadyExists();
    }
}


