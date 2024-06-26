package com.juliaosistem.mcs_financial.api.dtos.request;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardRequest {

    private Integer id;

    @Size(min = 16, max = 16, message = "Card number must be exactly 16 characters")
    @Pattern(regexp = "\\d{16}", message = "Card number must contain only digits")
    private String cardNumber;
    private String firstName;
    private String lastName;
    private Boolean active ;
    private Double balance;
}
