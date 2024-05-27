package com.juliaosistem.mcs_financial.infrastructure.adapters.primary;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.CardServiceInter;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class CardImplTest {

    @Mock
    private UserResponses<CardResponse> userResponses;

    @Mock
    private CardServiceInter cardServiceInter;

    @InjectMocks
    private CardImpl cardImpl;

    @Test
    void testGenerateCardNumber_ValidId() {

        Integer validId = 123456;
        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        when(cardServiceInter.generateCardNumber(any(CardRequest.class))).thenReturn(expectedResponse);


        PlantillaResponse<CardResponse> response = cardImpl.generateCardNumber(validId);


        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(cardServiceInter, times(1)).generateCardNumber(any(CardRequest.class));
    }

    @Test
    void testGenerateCardNumber_InvalidId() {
        Integer invalidId = 123456;

        CardResponse cardResponse = CardResponse.builder().build();

        PlantillaResponse<CardResponse> expectedResponse = userResponses.buildResponse(ResponseType.NO_VALID_ID_CARD.getCode(), cardResponse);

        PlantillaResponse<CardResponse> response = cardImpl.generateCardNumber(invalidId);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testActivateCard_ValidCardRequest() {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setId(102030);
        cardRequest.setCardNumber("1020301234567801");

        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.GET.getMessage());
        expectedResponse.setData(CardResponse.builder()
                        .id(cardRequest.getId())
                        .cardNumber(cardRequest.getCardNumber())
                .build());

        when(cardServiceInter.findByCardNumber(cardRequest)).thenReturn(expectedResponse);
         when(cardServiceInter.activateCard(cardRequest)).thenReturn(expectedResponse);
        PlantillaResponse<CardResponse> response = cardImpl.activateCard(cardRequest);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testBlockCard_ValidCardId() {
        String cardId = "1020301234567801";
        PlantillaResponse<CardResponse> expectedResponse =  new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.LOCKED_CARD.getMessage());
        expectedResponse.setData(CardResponse.builder()
                        .id(102030)
                        .cardNumber(cardId)
                .build());

        when(cardServiceInter.findByCardNumber(any())).thenReturn(expectedResponse);

        PlantillaResponse<CardResponse> response = cardImpl.blockCard(cardId);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

    }

    @Test
    void testReloadBalance_ValidCardRequest() {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setId(102030);
        cardRequest.setCardNumber("1020301234567801");
        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.RELOAD_BALANCE.getMessage());
        expectedResponse.setData(CardResponse.builder()
                        .id(cardRequest.getId())
                        .cardNumber(cardRequest.getCardNumber())
                .build());
        when(cardServiceInter.findByCardNumber(cardRequest)).thenReturn(expectedResponse);

        PlantillaResponse<CardResponse> response = cardImpl.reloadBalance(cardRequest);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testCheckBalance_ValidCardId() {
        Integer cardId = 123;
        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        when(cardServiceInter.checkBalance(cardId)).thenReturn(expectedResponse);

        PlantillaResponse<CardResponse> response = cardImpl.checkBalance(cardId);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(cardServiceInter, times(1)).checkBalance(cardId);
    }
}
