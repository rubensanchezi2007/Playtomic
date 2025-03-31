package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletResponse;
import com.playtomic.tests.wallet.service.IWalletService;
import com.playtomic.tests.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/wallets")
public class WalletController {
    private static final Logger log = LoggerFactory.getLogger(WalletController.class);


    IWalletService walletService;

    @Autowired
    public WalletController(IWalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    ResponseEntity<WalletResponse> getWalletByID (@PathVariable long id)
    {
           log.info("Received request to GET wallet ID {}",id );
           Wallet wallet=walletService.getWalletByID(id);

            return ResponseEntity.ok( WalletResponse.builder()
                    .name(wallet.getName())
                    .balance(wallet.getBalance())
                    .depositList(wallet.getDepositList())
                    .build());

    }
    //void log() {
    //    log.info("Logging from /");
    //}
}
