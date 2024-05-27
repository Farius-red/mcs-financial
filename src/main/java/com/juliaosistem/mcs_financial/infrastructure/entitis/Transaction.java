package com.juliaosistem.mcs_financial.infrastructure.entitis;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="transacciones")
@Data
public class Transaction {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Cards card;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime dateOfPurchase;

    @Column(nullable = false)
    private Boolean annulled;

    @PrePersist
    public void prePersist() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateOfPurchase = LocalDateTime.now();
        String formattedDate = dateOfPurchase.format(formatter);
        this.dateOfPurchase = LocalDateTime.parse(formattedDate, formatter);
        if ( this.annulled == null ) annulled= false;
    }

}
