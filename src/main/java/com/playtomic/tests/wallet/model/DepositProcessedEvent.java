package com.playtomic.tests.wallet.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DepositProcessedEvent {
    private final String paymentId;
    private final Long requestId;
}
