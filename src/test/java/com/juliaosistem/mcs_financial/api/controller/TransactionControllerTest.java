package com.juliaosistem.mcs_financial.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.primary.TransactionService;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPurchaseTransaction() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest();

        PlantillaResponse<TransactionResponse> mockResponse = PlantillaResponse.<TransactionResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(new TransactionResponse())
                .build();

        when(transactionService.processPurchase(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/transaction/purchase")
                        .content(asJsonString(purchaseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(transactionService).processPurchase(any());
    }

    @Test
    void testGetTransactionById() throws Exception {


        PlantillaResponse<TransactionResponse> mockResponse = PlantillaResponse.<TransactionResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(new TransactionResponse())
                .build();

        when(transactionService.getTransactionById(any())).thenReturn(mockResponse);

        mockMvc.perform(get("/transaction/{transactionId}", UUID.fromString("7671518f-8d4d-417d-b00a-5fe0515d6732")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(transactionService).getTransactionById(any());
    }

    @Test
    void testAnulateTransaction() throws Exception {
        PurchaseRequest request = new PurchaseRequest();

        PlantillaResponse<TransactionResponse> mockResponse = PlantillaResponse.<TransactionResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(new TransactionResponse())
                .build();

        when(transactionService.anulateTransaction(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/transaction/anulation")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(transactionService).anulateTransaction(any());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
