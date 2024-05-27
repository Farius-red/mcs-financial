package com.juliaosistem.mcs_financial.infrastructure.repository;

import com.juliaosistem.mcs_financial.infrastructure.entitis.Cards;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Cards , Integer> {

    Cards findByCardNumber(String cardNumber);


    @Transactional
    @Query(value = "UPDATE tarjetas SET active = :active WHERE id = :id RETURNING *", nativeQuery = true)
    Cards  updateActive(@Param("active")Boolean active ,  @Param("id") Integer id );


}
