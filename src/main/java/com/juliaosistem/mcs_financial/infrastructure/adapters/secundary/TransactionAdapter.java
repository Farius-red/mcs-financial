package com.juliaosistem.mcs_financial.infrastructure.adapters.secundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.api.mappers.TransactionMapper;
import com.juliaosistem.mcs_financial.infrastructure.repository.CardRepository;
import com.juliaosistem.mcs_financial.infrastructure.repository.TransactionRepository;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.TransactionServiceInter;
import com.juliaosistem.mcs_financial.utils.AbtractError;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.MensajesRespuesta;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionAdapter  implements TransactionServiceInter {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule());
    private final AbtractError abtractError;
    private final UserResponses<TransactionResponse> userResponses;

    private  final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;
    private final CardRepository cardRepository;

    @Override
    public PlantillaResponse<TransactionResponse> processPruchase(PurchaseRequest purchaseRequest , Integer id) {
        try {
            var card = cardRepository.findById(id).orElseThrow();
            card.setBalance(card.getBalance()-purchaseRequest.getPrice());
           var transaction =   mapper.map(purchaseRequest);
           transaction.setCard(card);
           var response = mapper.map(transactionRepository.save(transaction));
              return userResponses.buildResponse(ResponseType.BUY_SUCCESS.getCode(), response);
        }catch (Exception e){
             abtractError.logError(e);
             return  userResponses.buildResponse(ResponseType.FALLO.getCode(), TransactionResponse.builder().build() );
        }
    }

    @Override
    public PlantillaResponse<TransactionResponse> getTransactionById(UUID transactionId) {
        try {
           var transaction=  transactionRepository.findById(transactionId);
           if(transaction.isPresent()){
               var response = mapper.map(transaction.get());
            abtractError.logInfo("getTransactionById() :" + MensajesRespuesta.GET.getMensaje() + "Con transactionID = " + OBJECT_MAPPER.writeValueAsString(transactionId));
           return   userResponses.buildResponse(ResponseType.GET.getCode(), response);
           }else
               return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), TransactionResponse.builder().id(transactionId).build());
        }catch (Exception e){
            abtractError.logError(e);
            return  userResponses.buildResponse(ResponseType.FALLO.getCode(), TransactionResponse.builder().build() );
        }
    }

    @Override
    public PlantillaResponse<TransactionResponse> anulateTransaction(PurchaseRequest request , CardResponse cardResponse) {
        try {

            var card = cardRepository.findById(cardResponse.getId()).orElseThrow();
            card.setBalance(card.getBalance() + request.getPrice());
            var transaction = transactionRepository.findById(request.getId()).orElseThrow();
               transaction.setAnnulled(true);
               transaction.setCard(card);
             var response = mapper.map(transactionRepository.save(transaction));
             abtractError.logInfo("anulateTransaction() :" + MensajesRespuesta.TRANSACTION_SUCCESS_NULL.getMensaje() + " transactionID = " + OBJECT_MAPPER.writeValueAsString(request));
             return  userResponses.buildResponse(ResponseType.TRANSACTION_SUCCESS_NULL.getCode(),response);
        }catch (Exception e) {
            abtractError.logError(e);
            return  userResponses.buildResponse(ResponseType.FALLO.getCode(), TransactionResponse.builder().build() );
        }
    }
}
