package com.juliaosistem.mcs_financial.infrastructure.adapters.secundary;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.mappers.CardMapper;
import com.juliaosistem.mcs_financial.infrastructure.entitis.Cards;
import com.juliaosistem.mcs_financial.infrastructure.repository.CardRepository;
import com.juliaosistem.mcs_financial.utils.AbtractError;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardAdapterTest {

    @Mock
    private  CardRepository cardRepository;
    @Mock
    private  UserResponses<CardResponse> userResponses;

    @Mock
    private AbtractError abtractError;

    @Mock
    private   CardMapper cardMapper;

    @InjectMocks
    private CardAdapter cardAdapter;


    @Test
    void generateCardNumber() {

        var carRequest = CardRequest.builder().id(102030)
                .cardNumber("1020301234567801")
                .build();

        var expectedMapResponse = CardResponse.builder()
                .cardNumber("1020301234567801")
                .build();
        var expectectCardsResponse  = Cards.builder().id(102030)
                        .blocked(false)
                                .active(false).build();

        when(cardRepository.save(any())).thenReturn(expectectCardsResponse);
        when(cardMapper.map(any())).thenReturn(expectedMapResponse);
        when(userResponses.buildResponse(ResponseType.CREATED.getCode(), expectedMapResponse)).thenReturn(new PlantillaResponse<>());


        var response = cardAdapter.generateCardNumber(carRequest);

        assertNotNull(response);

    }
}