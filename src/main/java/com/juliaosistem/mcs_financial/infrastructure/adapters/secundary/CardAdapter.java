package com.juliaosistem.mcs_financial.infrastructure.adapters.secundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.mappers.CardMapper;
import com.juliaosistem.mcs_financial.infrastructure.entitis.Cards;
import com.juliaosistem.mcs_financial.infrastructure.repository.CardRepository;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.CardServiceInter;
import com.juliaosistem.mcs_financial.utils.AbtractError;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.MensajesRespuesta;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CardAdapter implements CardServiceInter {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule());
    private final AbtractError abtractError;
    private final UserResponses<CardResponse> userResponses;

    private final CardRepository cardRepository;
    private  final CardMapper cardMapper;


    @Override
    public PlantillaResponse<CardResponse> generateCardNumber(CardRequest cardRequest) {
        try {
         var  carRespose  = cardMapper.map(cardRepository.save(
                        Cards.builder()
                                .id(cardRequest.getId())
                                .cardNumber(cardRequest.getCardNumber())
                        .build()));
            abtractError.logInfo("generateCardNumber() :" + MensajesRespuesta.CREADO + " las direcciones  del usuario  " + OBJECT_MAPPER.writeValueAsString(cardRequest));
            return userResponses.buildResponse(ResponseType.CREATED.getCode(), carRespose );

        }catch (Exception e) {
            abtractError.logError(e);
          return   userResponses.buildResponse(ResponseType.FALLO.getCode(), CardResponse.builder().build() );
        }


    }

    @Override
    public PlantillaResponse<CardResponse> activateCard(CardRequest cardRequest) {
        try {
            var card  = cardRepository.updateActive(cardRequest.getActive() ,cardRequest.getId());
            abtractError.logInfo("activateCard() :" + MensajesRespuesta.CREADO + " se activo tarjeta   " + OBJECT_MAPPER.writeValueAsString(card));
            return  userResponses.buildResponse(ResponseType.CREATED_CARD.getCode(), CardResponse.builder()
                            .cardNumber(card.getCardNumber())
                    .build() );
        }catch (Exception e){
             abtractError.logError(e);
           return userResponses.buildResponse(ResponseType.FALLO.getCode(), CardResponse.builder().build());
        }
    }


   @Override
    public   PlantillaResponse<CardResponse>  findByCardNumber (CardRequest cardRequest){
       try {
           var card = cardRepository.findByCardNumber(cardRequest.getCardNumber());
           if( card != null && card.getId() != null  ) {
               var cardResponse = cardMapper.map(card);
               abtractError.logInfo("findByCardNumber() :" + MensajesRespuesta.CREADO.getMensaje() + " se encontro tarjeta   " + OBJECT_MAPPER.writeValueAsString(cardRequest.getCardNumber()));
               return userResponses.buildResponse(ResponseType.GET.getCode(), cardResponse);
           }else
               abtractError.logInfo("findByCardNumber() :" + MensajesRespuesta.NO_ENCONTRADO.getMensaje() + " de tarjeta   " + OBJECT_MAPPER.writeValueAsString(cardRequest.getCardNumber()));
           return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), CardResponse.builder().cardNumber(cardRequest.getCardNumber()).build() );

       }catch (Exception e){
           abtractError.logError(e);
           return userResponses.buildResponse(ResponseType.FALLO.getCode(),CardResponse.builder().build() );

       }
    }

    @Override
    public PlantillaResponse<CardResponse> blockCard(String cardId , Integer id) {
        try {

           var card = cardRepository.findById(id).orElseThrow();
              card.setBlocked(true);
            var cardResponse=  cardMapper.map(cardRepository.save(card));
            abtractError.logInfo("blockCard() :" + MensajesRespuesta.LOCKED_CARD.getMensaje() + ":idCard: =" + OBJECT_MAPPER.writeValueAsString(cardId));

            return userResponses.buildResponse(ResponseType.LOCKED_CARD.getCode(), cardResponse );
        }catch (Exception e){
            abtractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), CardResponse.builder().build());
        }
    }



    @Override
    public PlantillaResponse<CardResponse> reloadBalance(CardRequest cardRequest) {
        try{
            var optionalCard = cardRepository.findById(cardRequest.getId());
              var card = optionalCard.orElseThrow();
              card.setBalance(cardRequest.getBalance());
               var  cardResponse  = cardMapper.map(cardRepository.save(card));

            abtractError.logInfo("reloadBalance() :" + MensajesRespuesta.RELOAD_BALANCE .getMensaje()+ ":carRequest: =" + OBJECT_MAPPER.writeValueAsString(cardRequest));
            return userResponses.buildResponse(ResponseType.RELOAD_BALANCE.getCode(), cardResponse );

        }catch (Exception e){
             abtractError.logError(e);
             return  userResponses.buildResponse(ResponseType.FALLO.getCode(), CardResponse.builder().build());
        }
    }

    @Override
    public PlantillaResponse<CardResponse> checkBalance(Integer cardId) {
          try {
               var card = cardRepository.findById(cardId);
               if(card.isPresent()){
                    var cardResponse = cardMapper.map(card.get());
                   abtractError.logInfo("checkBalance() :" + MensajesRespuesta.GET.getMensaje() + ":idcard: =" + OBJECT_MAPPER.writeValueAsString(cardId));
                   return userResponses.buildResponse(ResponseType.GET.getCode(), cardResponse );
               }else
                   abtractError.logInfo("checkBalance() :" + MensajesRespuesta.NO_ENCONTRADO.getMensaje()+ ":idcard: =" + OBJECT_MAPPER.writeValueAsString(cardId));

              return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), CardResponse.builder().build());
          }catch (Exception e){
              abtractError.logError(e);
              return  userResponses.buildResponse(ResponseType.FALLO.getCode(), CardResponse.builder().build());
          }
    }
}
