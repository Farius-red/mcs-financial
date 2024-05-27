package com.juliaosistem.mcs_financial.infrastructure.services.primary;

import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.infrastructure.adapters.primary.TransactionsImpl;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionsImpl transactionImpl;

    public PlantillaResponse<TransactionResponse> processPurchase(PurchaseRequest purchaseRequest) {
        return transactionImpl.processPruchase(purchaseRequest);
    }

    public PlantillaResponse<TransactionResponse> getTransactionById(UUID transactionId) {
        return  transactionImpl.getTransactionById(transactionId);
    }

    public  PlantillaResponse<TransactionResponse> anulateTransaction(PurchaseRequest request) {
        return transactionImpl.anulateTransaction(request);
    }
}
