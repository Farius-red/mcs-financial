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
        Integer invalidId = 12345;

        CardResponse cardResponse = CardResponse.builder().build();

        PlantillaResponse<CardResponse> expectedResponse = userResponses.buildResponse(ResponseType.NO_VALID_ID_CARD.getCode(), cardResponse);
        when(userResponses.buildResponse(anyInt(), any())).thenReturn(expectedResponse);

        PlantillaResponse<CardResponse> response = cardImpl.generateCardNumber(invalidId);

        assertEquals(expectedResponse, response);
    }
}
