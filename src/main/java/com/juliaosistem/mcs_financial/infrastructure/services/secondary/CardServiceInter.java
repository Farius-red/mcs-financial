package com.juliaosistem.mcs_financial.infrastructure.services.secondary;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;

public interface CardServiceInter {

    PlantillaResponse<CardResponse> generateCardNumber(CardRequest cardRequest);
    PlantillaResponse<CardResponse> activateCard(CardRequest cardRequest);
    PlantillaResponse<CardResponse>   findByCardNumber (CardRequest cardRequest);
    PlantillaResponse<CardResponse> blockCard(String cardId , Integer id);
    PlantillaResponse<CardResponse> reloadBalance(CardRequest cardRequest);

    PlantillaResponse<CardResponse> checkBalance(Integer cardId);
}
