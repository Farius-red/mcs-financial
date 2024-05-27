package com.juliaosistem.mcs_financial.api.controller;

import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.primary.TransactionService;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@Tag(name = "transaction", description = "Endpoints relacionados a el manejo tarjetas y transaciones  ")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Transacción de compra", responses = {
            @ApiResponse(responseCode = "200", description = "Compra realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    @PostMapping("/purchase")
    public ResponseEntity<PlantillaResponse<TransactionResponse>> purchaseTransaction(@RequestBody PurchaseRequest purchaseRequest) {
        var response = transactionService.processPurchase(purchaseRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Consultar transacción", responses = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron datos correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron datos")
    })
    @GetMapping("/{transactionId}")
    public ResponseEntity<PlantillaResponse<TransactionResponse>> getTransactionById(@PathVariable UUID transactionId) {
        var response = transactionService.getTransactionById(transactionId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Anulación de transacción", responses = {
            @ApiResponse(responseCode = "200", description = "Transacción anulada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "No se encontraron datos")
    })
    @PostMapping("/anulation")
    public ResponseEntity<PlantillaResponse<TransactionResponse>> anulateTransaction(@RequestBody  PurchaseRequest request) {
        var response = transactionService.anulateTransaction(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
