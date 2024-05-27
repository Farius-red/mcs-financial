package com.juliaosistem.mcs_financial.api.dtos.responses;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardResponse {

    private Integer id;
    private String cardNumber;
    private Double balance ;
    private Boolean blocked;
}
