package com.playtomic.tests.wallet.api;

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

@RequestMapping("api/wallets")
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
            return ResponseEntity.ok(walletService.getWalletByID(id));

    }
    //void log() {
    //    log.info("Logging from /");
    //}
}
