package com.juliaosistem.mcs_financial.infrastructure.entitis;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tarjetas")
@Setter
@Getter
public class Cards {

    @Id
    private Integer id;

    @Size(min = 16, max = 16, message = "Card number must be exactly 16 characters")
    @Pattern(regexp = "\\d{16}", message = "Card number must contain only digits")
    @Column(unique = true, length = 16, nullable = false )
    private String cardNumber;


    private String firstName;

    private String lastName;

    private LocalDateTime expirationDate;

    private Boolean active;


    private Boolean blocked;


    private Double balance;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = generateSixDigitId();
        }
        if (this.active == null) {
            this.active = false;
        }

        if (this.blocked == null) {
            this.blocked = false;
        }


        if (this.balance == null){
            this.balance = 0.0;
        }


        if (this.expirationDate == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.expirationDate = LocalDateTime.now().plusYears(3);
            String formattedDate = expirationDate.format(formatter);
            this.expirationDate = LocalDateTime.parse(formattedDate, formatter);
        }
    }

    private Integer generateSixDigitId() {
        return 1000000 + ThreadLocalRandom.current().nextInt(900000);
    }
}
