package com.juliaosistem.mcs_financial.infrastructure.services.secondary;

import com.juliaosistem.mcs_financial.utils.PlantillaResponse;

public interface TransactionServiceInter {
    PlantillaResponse<Long> generateCard(Long idCard);
}
