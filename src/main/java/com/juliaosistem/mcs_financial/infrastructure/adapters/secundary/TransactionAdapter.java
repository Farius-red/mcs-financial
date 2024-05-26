package com.juliaosistem.mcs_financial.infrastructure.adapters.secundary;

import com.juliaosistem.mcs_financial.infrastructure.services.secondary.TransactionServiceInter;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionAdapter  implements TransactionServiceInter {


    private final UserResponses<Long> userResponses;

    @Override
    public PlantillaResponse<Long> generateCard(Long idCard) {
        return userResponses.buildResponse(ResponseType.CREATED.getCode(), idCard );
    }
}
