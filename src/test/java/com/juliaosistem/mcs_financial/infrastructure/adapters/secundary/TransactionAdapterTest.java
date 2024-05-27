package com.juliaosistem.mcs_financial.infrastructure.adapters.secundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.api.mappers.TransactionMapper;
import com.juliaosistem.mcs_financial.infrastructure.entitis.Cards;
import com.juliaosistem.mcs_financial.infrastructure.entitis.Transaction;
import com.juliaosistem.mcs_financial.infrastructure.repository.CardRepository;
import com.juliaosistem.mcs_financial.infrastructure.repository.TransactionRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TransactionAdapterTest {

    @Mock
    private AbtractError abtractError;

    @Mock
    private UserResponses<TransactionResponse> userResponses;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper mapper;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionAdapter transactionAdapter;

    @Test
    void testProcessPurchase_Success() {
        // Arrange
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setPrice(50.0);

        Cards card = new Cards();
        card.setId(123456);
        card.setBalance(100.0);

        when(cardRepository.findById(anyInt())).thenReturn(Optional.of(card));
        when(mapper.map(any(PurchaseRequest.class))).thenReturn(new Transaction());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                        .message(ResponseType.BUY_SUCCESS.getMessage())
                .build());


        PlantillaResponse<TransactionResponse> response = transactionAdapter.processPruchase(purchaseRequest, 1);

        assertNotNull(response);
        assertEquals(ResponseType.BUY_SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    void testProcessPurchase_Failure() {

        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setPrice(50.0);

        when(cardRepository.findById(anyInt())).thenThrow(new RuntimeException());

        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.FALLO.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionAdapter.processPruchase(purchaseRequest, 123456);

        assertNotNull(response);
        assertEquals(ResponseType.FALLO.getMessage(), response.getMessage());
    }

    @Test
    void testGetTransactionById_Found() throws JsonProcessingException {

        UUID transactionId = UUID.randomUUID();

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        when(transactionRepository.findById(any(UUID.class))).thenReturn(Optional.of(transaction));
        when(mapper.map(any(Transaction.class))).thenReturn(new TransactionResponse());
        when(userResponses.buildResponse(anyInt(), any())).thenReturn(new PlantillaResponse<>());

        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.GET.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionAdapter.getTransactionById(transactionId);


        assertNotNull(response);
        assertEquals(ResponseType.GET.getMessage(), response.getMessage());
    }

    @Test
    void testGetTransactionById_NotFound() throws JsonProcessingException {

        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());


        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.NO_ENCONTRADO.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionAdapter.getTransactionById(transactionId);

        assertNotNull(response);
        assertEquals(ResponseType.NO_ENCONTRADO.getMessage(), response.getMessage());
    }

    @Test
    void testGetTransactionById_Failure() {

        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.findById(any(UUID.class))).thenThrow(new RuntimeException());


        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.FALLO.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionAdapter.getTransactionById(transactionId);


        assertNotNull(response);
        assertEquals(ResponseType.FALLO.getMessage(), response.getMessage());
    }

    @Test
    void testAnulateTransaction_Success() {

        PurchaseRequest request = new PurchaseRequest();
        request.setId(UUID.randomUUID());
        request.setPrice(50.0);

        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(123456);

        Cards card = new Cards();
        card.setId(123456);
        card.setBalance(100.0);

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());

        when(cardRepository.findById(anyInt())).thenReturn(Optional.of(card));
        when(transactionRepository.findById(any(UUID.class))).thenReturn(Optional.of(transaction));
        when(mapper.map(any(Transaction.class))).thenReturn(new TransactionResponse());
        when(userResponses.buildResponse(anyInt(), any())).thenReturn(new PlantillaResponse<>());

        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.TRANSACTION_SUCCESS_NULL.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionAdapter.anulateTransaction(request, cardResponse);


        assertNotNull(response);
        assertEquals(ResponseType.TRANSACTION_SUCCESS_NULL.getMessage(), response.getMessage());
    }

    @Test
    void testAnulateTransaction_Failure() {

        PurchaseRequest request = new PurchaseRequest();
        request.setId(UUID.randomUUID());
        request.setPrice(50.0);

        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(123456);

        when(cardRepository.findById(anyInt())).thenReturn(Optional.empty());


        when(userResponses.buildResponse(anyInt(), any())).thenReturn(PlantillaResponse.<TransactionResponse>builder()
                .message(ResponseType.FALLO.getMessage())
                .build());

        PlantillaResponse<TransactionResponse> response = transactionAdapter.anulateTransaction(request, cardResponse);


        assertNotNull(response);
        assertEquals(ResponseType.FALLO.getMessage(), response.getMessage());
    }
}
