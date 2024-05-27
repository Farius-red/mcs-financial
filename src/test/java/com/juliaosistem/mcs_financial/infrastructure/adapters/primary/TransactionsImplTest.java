package com.juliaosistem.mcs_financial.infrastructure.adapters.primary;
import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.CardServiceInter;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.TransactionServiceInter;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TransactionsImplTest {

    @Mock
    private UserResponses<TransactionResponse> userResponses;

    @Mock
    private TransactionServiceInter transactionServiceInter;

    @Mock
    private CardServiceInter cardServiceInter;

    @InjectMocks
    private TransactionsImpl transactionsImpl;

    @Test
    void testProcessPurchase_CardNotFound() {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCardNumber("1234567890");

       PlantillaResponse<CardResponse> cardResponse = new PlantillaResponse<>();
         cardResponse.setMessage(ResponseType.CARD_NO_FOUND.getMessage());

        when(cardServiceInter.findByCardNumber(any())).thenReturn(cardResponse);
        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                        .message(ResponseType.CARD_NO_FOUND.getMessage())
                .build());


        PlantillaResponse<TransactionResponse> response = transactionsImpl.processPruchase(purchaseRequest);

        assertNotNull(response);
    }

    @Test
    void testProcessPurchase_CardBlocked() {

        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCardNumber("1234567890");
        purchaseRequest.setPrice(10.0);

        PlantillaResponse<CardResponse> cardResponse =
                new PlantillaResponse<>();
        cardResponse.setMessage(ResponseType.CARD_BLOCK.getMessage());
        cardResponse.setData(CardResponse.builder()
                        .balance(10.0)
                        .blocked(true)
                        .build());

        when(cardServiceInter.findByCardNumber(any())).thenReturn(cardResponse);

     when(userResponses.buildResponse(anyInt(),any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                     .message(ResponseType.CARD_BLOCK.getMessage())
             .build());
        PlantillaResponse<TransactionResponse> response = transactionsImpl.processPruchase(purchaseRequest);

        assertNotNull(response);
        assertEquals(ResponseType.CARD_BLOCK.getMessage(), response.getMessage());
    }

    @Test
    void testProcessPurchase_CardNotBlocked() {

        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCardNumber("1234567890");
        purchaseRequest.setId(UUID.randomUUID());
        purchaseRequest.setPrice(5.0);

        PlantillaResponse<CardResponse> cardResponse =
                new PlantillaResponse<>();
        cardResponse.setMessage(ResponseType.GET.getMessage());
        cardResponse.setData(CardResponse.builder()
                        .cardNumber(purchaseRequest.getCardNumber())
                        .id(123456)
                        .blocked(false)
                        .balance(100.0)
                .build());

        when(cardServiceInter.findByCardNumber(any())).thenReturn(cardResponse);



         when(userResponses.buildResponse(anyInt(),any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                         .message(ResponseType.CREATED.getMessage())
                 .build());
        PlantillaResponse<TransactionResponse> response = transactionsImpl.processPruchase(purchaseRequest);

        assertNotNull(response);
        assertEquals(ResponseType.CREATED.getMessage(), response.getMessage());
    }

    @Test
    void testAnulateTransaction_TransactionNotFound() {

        PurchaseRequest request = new PurchaseRequest();
        request.setId(UUID.randomUUID());


        when(transactionServiceInter.getTransactionById(any())).thenReturn(
                PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.TRANSACTION_FAIL.getMessage())
                .build());


        PlantillaResponse<TransactionResponse> response = transactionsImpl.anulateTransaction(request);


        assertNotNull(response);
        assertEquals(ResponseType.TRANSACTION_FAIL.getMessage(), response.getMessage());
    }

    @Test
    void testAnulateTransaction_TransactionFound_CardNotFound() {

        PurchaseRequest request = new PurchaseRequest();
        request.setId(UUID.randomUUID());
        request.setCardNumber("1234567890");


        when(transactionServiceInter.getTransactionById(any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                        .message(ResponseType.GET.getMessage())
                        .data(TransactionResponse.builder()
                                .dateOfPurchase(LocalDateTime.now())
                                .build())
                .build());

        when(cardServiceInter.findByCardNumber(any())).thenReturn(PlantillaResponse.<CardResponse>builder()
                        .message(ResponseType.CARD_NO_FOUND.getMessage())
                .build());
        when(userResponses.buildResponse(anyInt(),any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                        .message(ResponseType.CARD_NO_FOUND.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionsImpl.anulateTransaction(request);


        assertNotNull(response);
        assertEquals(ResponseType.CARD_NO_FOUND.getMessage(), response.getMessage());
    }

    @Test
    void testAnulateTransaction_TransactionFound_CardFound_CardBlocked() {

        PurchaseRequest request = new PurchaseRequest();
        request.setId(UUID.randomUUID());
        request.setCardNumber("1234567890");


        PlantillaResponse<CardResponse> cardResponse =
                PlantillaResponse.<CardResponse>builder()
                        .message(ResponseType.GET.getMessage())
                        .data(CardResponse.builder()
                                .cardNumber(request.getCardNumber())
                                .id(123456)
                                .blocked(true)
                                .build())
                        .build();

        when(transactionServiceInter.getTransactionById(any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                        .message(ResponseType.GET.getMessage())
                        .data(TransactionResponse.builder()
                                .dateOfPurchase(LocalDateTime.now())
                                .build())
                .build());


        when(cardServiceInter.findByCardNumber(any())).thenReturn(cardResponse);

        when(userResponses.buildResponse(anyInt(),any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                        .message(ResponseType.CARD_BLOCK.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionsImpl.anulateTransaction(request);

        assertNotNull(response);
        assertEquals(ResponseType.CARD_BLOCK.getMessage(), response.getMessage());
    }

    @Test
    void testAnulateTransaction_TransactionFound_CardFound_CardNotBlocked() {


        PurchaseRequest request = new PurchaseRequest();
        request.setId(UUID.randomUUID());
        request.setCardNumber("1234567890");

        PlantillaResponse<CardResponse> cardResponse =
                PlantillaResponse.<CardResponse>builder()
                        .message(ResponseType.GET.getMessage())
                        .data(CardResponse.builder()
                                .cardNumber(request.getCardNumber())
                                .id(123456)
                                .blocked(true)
                                .build())
                        .build();

        when(transactionServiceInter.getTransactionById(any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.GET.getMessage())
                .data(TransactionResponse.builder()
                        .dateOfPurchase(LocalDateTime.now())
                        .build())
                .build());


        when(cardServiceInter.findByCardNumber(any())).thenReturn(cardResponse);



        when(userResponses.buildResponse(anyInt(),any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.GET.getMessage())
                .build());
        PlantillaResponse<TransactionResponse> response = transactionsImpl.anulateTransaction(request);

        assertNotNull(response);
    }



}
