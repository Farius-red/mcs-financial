package com.juliaosistem.mcs_financial.api.mappers;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.infrastructure.entitis.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel ="spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CardMapper {


    CardResponse map ( Cards source);

    Cards mapCardsByCardRequest(CardRequest source);

}
