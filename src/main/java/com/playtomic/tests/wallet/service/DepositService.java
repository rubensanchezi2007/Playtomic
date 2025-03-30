package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.api.DepositController;
import com.playtomic.tests.wallet.exception.DepositException;
import com.playtomic.tests.wallet.model.*;
import com.playtomic.tests.wallet.repository.DepositRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DepositService implements IDepositService {

    private static final Logger log = LoggerFactory.getLogger(DepositService.class);


    private final DepositRepository depositRepository;
    private final StripeService stripeService;
    private final WalletService walletService;

    @Autowired
    public DepositService(DepositRepository depositRepository, StripeService stripeService, WalletService walletService) {
        this.depositRepository = depositRepository;
        this.stripeService = stripeService;
        this.walletService = walletService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void generateVerificationToken(DepositProcessedEvent depositProcessedEvent) {
        stripeService.refund(depositProcessedEvent.getPaymentId());
    }

    @Override
    public DepositResponse processDeposit(DepositRequest depositRequest) {
        if (depositRepository.findByRequestId(depositRequest.getRequestId()).isEmpty())
        {
            log.info("New deposit detected requestId {}",depositRequest.getRequestId());

            Wallet wallet= walletService.getWalletByID(depositRequest.getWalletId());
            Deposit deposit= new Deposit();
            deposit.setAmount(depositRequest.getAmount());
            deposit.setRequestId(depositRequest.getRequestId());
            deposit.setStatus("PENDING");
            deposit.setWallet(wallet);
            ;

            depositRepository.save(deposit);


            //Payment payment=stripeService.charge(depositRequest.getCardNumber(),depositRequest.getAmount());

            deposit.setStatus("APPROVED");

            depositRepository.save(deposit);

            walletService.updateBalance(wallet.getId(),depositRequest.getAmount(),"12345");

            return DepositResponse.builder().amount(depositRequest.getAmount())
                    .currency(wallet.getCurrency())
                    .status(deposit.getStatus())
                    .transactionId("12345").build();
        }

        throw DepositException.alreadyExists();
    }
}


