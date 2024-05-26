package com.juliaosistem.mcs_financial.api.controller;

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

}