package com.juliaosistem.mcs_financial.infrastructure.adapters.primary;

import com.juliaosistem.mcs_financial.infrastructure.services.secondary.TransactionServiceInter;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionsImpl {
    private  final TransactionServiceInter transactionServiceInter;

    PlantillaResponse<Long> generateCard(){
        return null;
    }


}
