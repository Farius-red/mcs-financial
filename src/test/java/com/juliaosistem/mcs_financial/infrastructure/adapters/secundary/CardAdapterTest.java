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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void testFindByCardNumber_CardFound()  {

        String cardNumber = "1234567890123456";
        CardRequest cardRequest = CardRequest.builder().cardNumber(cardNumber).build();

        Cards card = new Cards();
        card.setId(123456);
        card.setCardNumber(cardNumber);

        CardResponse cardResponse = CardResponse.builder().id(1).cardNumber(cardNumber).build();

        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        when(cardMapper.map(any(Cards.class))).thenReturn(cardResponse);

        when(userResponses.buildResponse(anyInt(), any())).thenReturn(
                PlantillaResponse.<CardResponse>builder()
                        .message(ResponseType.GET.getMessage())
                        .build()
        );


        PlantillaResponse<CardResponse> response = cardAdapter.findByCardNumber(cardRequest);

        assertNotNull(response);
        assertEquals(ResponseType.GET.getMessage(), response.getMessage());
    }

    @Test
    void testFindByCardNumber_CardNotFound() {
        String cardNumber = "1234567890123456";
        CardRequest cardRequest = CardRequest.builder().cardNumber(cardNumber).build();

        when(cardRepository.findByCardNumber(anyString())).thenReturn(null);

        when(userResponses.buildResponse(anyInt(), any())).thenReturn(
                PlantillaResponse.<CardResponse>builder()
                        .message(ResponseType.NO_ENCONTRADO.getMessage())
                        .build()
        );

        PlantillaResponse<CardResponse> response = cardAdapter.findByCardNumber(cardRequest);

        assertNotNull(response);
        assertEquals(ResponseType.NO_ENCONTRADO.getMessage(), response.getMessage());
    }

    @Test
    void testFindByCardNumber_Exception() {

        String cardNumber = "1234567890123456";
        CardRequest cardRequest = CardRequest.builder().cardNumber(cardNumber).build();

        when(cardRepository.findByCardNumber(anyString())).thenThrow(new RuntimeException());

        when(userResponses.buildResponse(anyInt(), any())).thenReturn(
                PlantillaResponse.<CardResponse>builder()
                        .message(ResponseType.FALLO.getMessage())
                        .build()
        );

        PlantillaResponse<CardResponse> response = cardAdapter.findByCardNumber(cardRequest);


        assertNotNull(response);
        assertEquals(ResponseType.FALLO.getMessage(), response.getMessage());
    }

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


    @Test
    void testActivateCard_Success() {

        CardRequest cardRequest = CardRequest.builder()
                .id(1)
                .active(true)
                .build();
        Cards card = Cards.builder()
                .id(1)
                .cardNumber("1234567890")
                .build();

        when(cardRepository.updateActive(anyBoolean(), anyInt())).thenReturn(card);
        when(userResponses.buildResponse(anyInt(), any(CardResponse.class))).thenReturn(new PlantillaResponse<>());

        PlantillaResponse<CardResponse> response = cardAdapter.activateCard(cardRequest);
        assertNotNull(response);
    }

    @Test
    void testActivateCard_Failure()  {

        CardRequest cardRequest = CardRequest.builder()
                .id(1)
                .active(true)
                .build();

        when(cardRepository.updateActive(anyBoolean(), anyInt())).thenThrow(new RuntimeException());
        when(userResponses.buildResponse(anyInt(), any(CardResponse.class))).thenReturn(new PlantillaResponse<>());


        PlantillaResponse<CardResponse> response = cardAdapter.activateCard(cardRequest);

        assertNotNull(response);
    }

    @Test
    void testBlockCard_Success(){

        String cardId = "1234567890";
        Integer id = 1;
        Cards card = Cards.builder()
                .id(id)
                .cardNumber(cardId)
                .build();

        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        when(cardRepository.save(any())).thenReturn(card);
        when(userResponses.buildResponse(anyInt(), any(CardResponse.class))).thenReturn(new PlantillaResponse<>());

        // Act
        PlantillaResponse<CardResponse> response = cardAdapter.blockCard(cardId, id);

        // Assert
        assertNotNull(response);
        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testBlockCard_Failure()  {

        String cardId = "1234567890";
        Integer id = 123456;
        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.FALLO.getMessage());
        expectedResponse.setData(CardResponse.builder()
                .id(id)
                .cardNumber(cardId)
                .build());

        when(userResponses.buildResponse(anyInt(),any())).thenReturn(expectedResponse);
        when(cardRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        PlantillaResponse<CardResponse> response = cardAdapter.blockCard(cardId, id);

        // Assert
        assertNotNull(response);
    }

    @Test
    void testReloadBalance_Success()  {

        CardRequest cardRequest = CardRequest.builder()
                .id(123456)
                .balance(10.0)
                .build();
        Cards card = Cards.builder()
                .id(123456)
                .balance(0.0)
                .build();

        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.GET.getMessage());
        expectedResponse.setData(CardResponse.builder()
                .id(cardRequest.getId())
                .cardNumber(cardRequest.getCardNumber())
                .build());

        when(cardRepository.findById(anyInt())).thenReturn(Optional.of(card));
        when(cardRepository.save(any())).thenReturn(card);
        when(userResponses.buildResponse(anyInt(), any())).thenReturn(expectedResponse);

        // Act
        PlantillaResponse<CardResponse> response = cardAdapter.reloadBalance(cardRequest);

        // Assert
        assertNotNull(response);
    }

    @Test
    void testReloadBalance_Failure() {

        CardRequest cardRequest = CardRequest.builder()
                .id(123456)
                .balance(10.0)
                .build();


        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.FALLO.getMessage());
        expectedResponse.setData(CardResponse.builder()
                .id(cardRequest.getId())
                .cardNumber(cardRequest.getCardNumber())
                .build());

        when(cardRepository.findById(anyInt())).thenReturn(Optional.empty());

        when(userResponses.buildResponse(anyInt(),any())).thenReturn(expectedResponse);

        PlantillaResponse<CardResponse> response = cardAdapter.reloadBalance(cardRequest);

        assertNotNull(response);
    }

    @Test
    void testCheckBalance_Success()  {

        Integer cardId = 123456;
        Cards card = Cards.builder()
                .id(cardId)
                .balance(10.0)
                .build();

       var carResponse = CardResponse.builder()
                .cardNumber("1234567891234567")
                .balance(10.0)
                .id(cardId)
                .build();


        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.GET.getMessage());
        expectedResponse.setData(CardResponse.builder()
                .id(carResponse.getId())
                .cardNumber(carResponse.getCardNumber())
                .build());
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));


        when(cardMapper.map(any())).thenReturn(carResponse);

        when(userResponses.buildResponse(anyInt(),any())).thenReturn(expectedResponse);

        PlantillaResponse<CardResponse> response = cardAdapter.checkBalance(cardId);

        assertNotNull(response);
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    void testCheckBalance_Failure()  {

        Integer cardId = 123456;

        PlantillaResponse<CardResponse> expectedResponse = new PlantillaResponse<>();
        expectedResponse.setMessage(ResponseType.FALLO.getMessage());


        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        when(userResponses.buildResponse(anyInt(),any())).thenReturn(expectedResponse);
        PlantillaResponse<CardResponse> response = cardAdapter.checkBalance(cardId);

        assertNotNull(response);
    }
}