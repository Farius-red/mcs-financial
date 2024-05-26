package com.juliaosistem.mcs_financial.api.dtos.request;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardRequest {

    private Integer id;
    private String cardNumber;
    private String firstName;
    private String lastName;
    private Boolean active ;

}
