package com.juliaosistem.mcs_financial.api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("dateOfPurchase")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfPurchase;

    @JsonProperty("annulled")
    private boolean annulled;
}
