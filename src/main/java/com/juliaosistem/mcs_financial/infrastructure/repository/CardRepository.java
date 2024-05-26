package com.juliaosistem.mcs_financial.infrastructure.repository;

import com.juliaosistem.mcs_financial.infrastructure.entitis.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Cards , Integer> {

    Cards findByCardNumber(String cardNumber);

}
