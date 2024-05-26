package com.juliaosistem.mcs_financial.infrastructure.services.primary;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.infrastructure.adapters.primary.CardImpl;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardImpl cardImpl;


    public PlantillaResponse<CardResponse> generateCardNumber(Integer idCard){
        return cardImpl.generateCardNumber(idCard);
    }


    public PlantillaResponse<CardResponse> activateCard(CardRequest cardRequest){
        return cardImpl.activateCard(cardRequest);
    }

    public PlantillaResponse<CardResponse> blockCard(Integer cardId) {
        return cardImpl.blockCard(cardId);
    }
}
