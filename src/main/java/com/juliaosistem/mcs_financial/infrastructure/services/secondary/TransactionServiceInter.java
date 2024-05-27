package com.juliaosistem.mcs_financial.infrastructure.services.secondary;

import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;

import java.util.UUID;

public interface TransactionServiceInter {

   PlantillaResponse<TransactionResponse>processPruchase(PurchaseRequest purchaseRequest ,Integer id);

   PlantillaResponse<TransactionResponse> getTransactionById(UUID transactionId);

   PlantillaResponse<TransactionResponse> anulateTransaction(PurchaseRequest request, CardResponse cardResponse);
}
