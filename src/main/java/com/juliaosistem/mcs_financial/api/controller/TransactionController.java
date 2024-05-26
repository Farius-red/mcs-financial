package com.juliaosistem.mcs_financial.api.controller;

import com.juliaosistem.mcs_financial.infrastructure.entitis.Transaction;
import com.juliaosistem.mcs_financial.infrastructure.services.primary.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Tag(name = "transaction", description = "Endpoints relacionados a el manejo tarjetas y transaciones  ")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

}
