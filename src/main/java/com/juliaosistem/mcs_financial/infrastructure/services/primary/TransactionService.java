package com.juliaosistem.mcs_financial.infrastructure.services.primary;

import com.juliaosistem.mcs_financial.infrastructure.adapters.primary.TransactionsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionsImpl transactionImpl;
}
