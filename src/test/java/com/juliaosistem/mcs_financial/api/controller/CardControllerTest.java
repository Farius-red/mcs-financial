package com.juliaosistem.mcs_financial.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.primary.CardService;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import net.minidev.json.JSONObject;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CardController.class)
@AutoConfigureMockMvc(addFilters = false)
class CardControllerTest {



    @MockBean
    private CardService cardService;


    @Autowired
    private MockMvc mockMvc;


    @Test
    void testGetCardNumber() throws Exception {

        Integer productId = 102030;

        when(cardService.generateCardNumber(productId)).thenReturn(PlantillaResponse.<CardResponse>builder()
                        .httpStatus(HttpStatus.OK)
                .build());


        mockMvc.perform(get("/card/{productId}/number", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(cardService).generateCardNumber(productId);
    }


@Test
void testCreateCard() throws Exception {

    JSONObject requestBody = new JSONObject();
    requestBody.put("id", 102030);
    requestBody.put("cardNumber", "1020301234567801");
    requestBody.put("firstName", "Daniel ");
    requestBody.put("lastName", "Juliao");
    requestBody.put("active", true);

    when(cardService.activateCard(any())).thenReturn(PlantillaResponse.<CardResponse>builder()
                    .httpStatus(HttpStatus.OK)
                    .rta(true)
            .build());
    mockMvc.perform(post("/card/enroll")
                    .content(requestBody.toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
}

    @Test
    void testReloadBalance() throws Exception {

        CardRequest cardRequest = new CardRequest();
        cardRequest.setId(123456);
        cardRequest.setBalance(100.0);


        PlantillaResponse<CardResponse> mockResponse = PlantillaResponse.<CardResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(new CardResponse())
                .build();
        when(cardService.reloadBalance(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/card/balance")
                        .content(asJsonString(cardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(cardService).reloadBalance(any());
    }

    @Test
    void testCheckBalance() throws Exception {
        Integer cardId = 123456;

        PlantillaResponse<CardResponse> mockResponse = PlantillaResponse.<CardResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(new CardResponse())
                .build();
        when(cardService.checkBalance(anyInt())).thenReturn(mockResponse);


        mockMvc.perform(get("/card/balance/{cardId}", cardId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        verify(cardService).checkBalance(anyInt());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBlockCard() throws Exception {
        String cardId = "123467891234567";

        PlantillaResponse<CardResponse> mockResponse = PlantillaResponse.<CardResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(new CardResponse())
                .build();
        when(cardService.blockCard(anyString())).thenReturn(mockResponse);

        mockMvc.perform(delete("/card/{cardId}", cardId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andReturn();
        verify(cardService).blockCard(anyString());
    }
}