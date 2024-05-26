package com.juliaosistem.mcs_financial.infrastructure.entitis;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(unique = true, length = 16, nullable = false )
    private String cardNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean blocked;

    @Column(nullable = false)
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
            this.expirationDate = LocalDate.now().plusYears(3);
        }
    }

    private Integer generateSixDigitId() {
        return 1000000 + ThreadLocalRandom.current().nextInt(900000);
    }
}
