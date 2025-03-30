package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.DepositRequest;
import com.playtomic.tests.wallet.model.DepositResponse;
import com.playtomic.tests.wallet.model.WalletResponse;
import com.playtomic.tests.wallet.service.IDepositService;
import com.playtomic.tests.wallet.service.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("api/deposits")
public class DepositController {
    private static final Logger log = LoggerFactory.getLogger(DepositController.class);


    IDepositService depositService;

    @Autowired
    public DepositController(IDepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/")
    ResponseEntity<DepositResponse> processDeposit (@RequestBody DepositRequest depositRequest)
    {
        log.info("Processing deposit requestId {} amount {}",depositRequest.getRequestId(),depositRequest.getAmount());
            return ResponseEntity.ok(depositService.processDeposit(depositRequest));

    }
    //void log() {
    //    log.info("Logging from /");
    //}
}
