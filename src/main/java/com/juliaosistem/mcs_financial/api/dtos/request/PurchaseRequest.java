package com.juliaosistem.mcs_financial.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseRequest {


    private UUID id;

    @Size(min = 16, max = 16, message = "Card number must be exactly 16 characters")
    @Pattern(regexp = "\\d{16}", message = "Card number must contain only digits")
    @JsonProperty("cardNumber")
    @NotNull
    private String cardNumber;

    @JsonProperty("price")
    private Double price;
}
