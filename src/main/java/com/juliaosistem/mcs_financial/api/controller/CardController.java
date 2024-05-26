package com.juliaosistem.mcs_financial.api.controller;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.primary.CardService;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
    @RequestMapping("/card")
    @Tag(name = "card", description = "Endpoints relacionados a el manejo tarjetas creacion, activaciones  y demas   ")
    @RequiredArgsConstructor
    public class CardController {

        private final CardService cardService;


        @Operation(summary = "Genera numero de tarjeta", responses = {
                @ApiResponse(responseCode = "200", description = "Genera el numero de tarjeta")
        })
        @GetMapping("/{productId}/number")
        public ResponseEntity<PlantillaResponse<CardResponse>> getCardNumber(@PathVariable("productId") Integer idCard) {
            var response = cardService.generateCardNumber(idCard);
            return new ResponseEntity<>(response, response.getHttpStatus());
        }


        @Operation(summary = "Activar tarjeta", responses = {
                @ApiResponse(responseCode = "200", description = "Tarjeta activada exitosamente")
        })
        @PostMapping("/enroll")
        public ResponseEntity<PlantillaResponse<CardResponse>> createCard( @RequestBody CardRequest cardRequest) {
            var response = cardService.activateCard(cardRequest);
            return new ResponseEntity<>(response, response.getHttpStatus());
        }


    @Operation(summary = "Bloquear tarjeta", responses = {
            @ApiResponse(responseCode = "200", description = "Tarjeta bloqueada exitosamente")
    })
    @DeleteMapping("/{cardId}")
    public ResponseEntity<PlantillaResponse<CardResponse>> blockCard(@PathVariable("cardId") Integer cardId) {
        var response = cardService.blockCard(cardId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
