package com.juliaosistem.mcs_financial.api.mappers;

import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.infrastructure.entitis.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel ="spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TransactionMapper {

    @Mapping(target = "amount" , source = "price")
    Transaction map(PurchaseRequest source);

    @Mapping(target = "dateOfPurchase", source = "dateOfPurchase", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "price" , source = "amount")
    TransactionResponse map(Transaction soruce);

}
