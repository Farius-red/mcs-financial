package com.juliaosistem.mcs_financial.infrastructure.adapters.primary;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.CardServiceInter;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CardImpl {

    private final UserResponses<CardResponse> userResponses;
    private  final CardServiceInter cardServiceInter;

   public  PlantillaResponse<CardResponse> generateCardNumber(Integer idCard){
        if(isSixDigitLong(idCard)){
            return  cardServiceInter.generateCardNumber(CardRequest.builder()
                    .id(idCard)
                    .cardNumber(createCardNumber(idCard))
                    .build());
        }else
            return userResponses.buildResponse(ResponseType.NO_VALID_ID_CARD.getCode(), CardResponse.builder().build());

    }

    private  boolean isSixDigitLong(Integer value) {
        if (value == null) {
            return false;
        }
        String valueStr = value.toString();
        return valueStr.length() == 6 && valueStr.matches("\\d{6}");
    }


    private  String generateRandomDigits() {

        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }

    private String createCardNumber(Integer idPart) {
        String idString = String.format("%06d", idPart);
        String additionalDigits = generateRandomDigits();
        return idString + additionalDigits;
    }


    public  PlantillaResponse<CardResponse> activateCard(CardRequest cardRequest) {
        var response = cardServiceInter.findByCardNumber(cardRequest);

        if (response.getMessage().equalsIgnoreCase(ResponseType.GET.getMessage())) {
            response = cardServiceInter.activateCard(cardRequest);
            return response;
        } else
            return response;
    }

        public PlantillaResponse<CardResponse> blockCard(Integer cardId) {

       var response = cardServiceInter.findByCardNumber( CardRequest.builder().id(cardId).build());
            if (response.getMessage().equalsIgnoreCase(ResponseType.GET.getMessage())) {
                response = cardServiceInter.blockCard(cardId);
                return response;
            } else
                return response;
        }


}
