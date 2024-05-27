package com.juliaosistem.mcs_financial.infrastructure.adapters.primary;

import com.juliaosistem.mcs_financial.api.dtos.request.CardRequest;
import com.juliaosistem.mcs_financial.api.dtos.request.PurchaseRequest;
import com.juliaosistem.mcs_financial.api.dtos.responses.CardResponse;
import com.juliaosistem.mcs_financial.api.dtos.responses.TransactionResponse;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.CardServiceInter;
import com.juliaosistem.mcs_financial.infrastructure.services.secondary.TransactionServiceInter;
import com.juliaosistem.mcs_financial.utils.PlantillaResponse;
import com.juliaosistem.mcs_financial.utils.UserResponses;
import com.juliaosistem.mcs_financial.utils.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionsImpl {

    private  final UserResponses<TransactionResponse> userResponses;

    private  final TransactionServiceInter transactionServiceInter;
    private final CardServiceInter cardServiceInter;


    public PlantillaResponse<TransactionResponse> processPruchase(PurchaseRequest purchaseRequest) {

       var cardResponse = cardServiceInter.findByCardNumber(CardRequest.builder().cardNumber(purchaseRequest.getCardNumber()).build());
        if (cardResponse.getMessage().equalsIgnoreCase(ResponseType.GET.getMessage())) {
             if(purchaseRequest.getPrice() <= cardResponse.getData().getBalance()) {
                 return   verifiedCardBlock(cardResponse,purchaseRequest);
             }else
                return userResponses.buildResponse(ResponseType.CARD_DONT_HAVE_FOUNDS.getCode(), TransactionResponse.builder().build());
        }
        return userResponses.buildResponse(ResponseType.CARD_NO_FOUND.getCode(), TransactionResponse.builder().build());
    }


    private PlantillaResponse<TransactionResponse> verifiedCardBlock(PlantillaResponse<CardResponse> cardResponse ,PurchaseRequest purchaseRequest ){
        if(Boolean.FALSE.equals(cardResponse.getData().getBlocked())){
            return userResponses.buildResponse(ResponseType.CARD_BLOCK.getCode(), TransactionResponse.builder().build());
        }else {
            return transactionServiceInter.processPruchase(purchaseRequest, cardResponse.getData().getId());
        }
    }

    public PlantillaResponse<TransactionResponse> getTransactionById(UUID transactionId) {
        return transactionServiceInter.getTransactionById(transactionId);
    }

    public PlantillaResponse<TransactionResponse> anulateTransaction(PurchaseRequest request) {
    var transactionByIdResponse = getTransactionById(request.getId());
     if(transactionByIdResponse.getMessage().equalsIgnoreCase(ResponseType.GET.getMessage())){
         var cardResponse =verifiedCardNumber(CardRequest.builder().cardNumber(request.getCardNumber()).build(),transactionByIdResponse);
            if(cardResponse.isRta()) {
                request.setPrice(transactionByIdResponse.getData().getPrice());
                return transactionServiceInter.anulateTransaction(request, cardResponse.getData());
            }
            else {
                if(cardResponse.getMessage().equalsIgnoreCase(ResponseType.CARD_NO_FOUND.getMessage())){
                    return userResponses.buildResponse(ResponseType.CARD_NO_FOUND.getCode(), TransactionResponse.builder().build());
                }
                if(cardResponse.getMessage().equalsIgnoreCase(ResponseType.TRANSACTION_FAIL.getMessage())) {
                    return userResponses.buildResponse(ResponseType.TRANSACTION_FAIL.getCode(), TransactionResponse.builder().build());
                }
                return userResponses.buildResponse(ResponseType.FALLO.getCode(), TransactionResponse.builder().build());
            }
      } else return transactionByIdResponse;
    }

    private PlantillaResponse<CardResponse>  verifiedCardNumber(CardRequest cardRequest , PlantillaResponse<TransactionResponse> transactionByIdResponse){
        if(!verifiedDateOfPurchese(transactionByIdResponse.getData())) {
          return cardServiceInter.findByCardNumber(cardRequest);
        }
        return PlantillaResponse.<CardResponse>builder().rta(false)
                .message(ResponseType.TRANSACTION_FAIL.getMessage())
                .build();
    }

    private boolean  verifiedDateOfPurchese(TransactionResponse transactionResponse){
        var dateOfPurchase = transactionResponse.getDateOfPurchase();
       return dateOfPurchase.isAfter(transactionResponse.getDateOfPurchase().plusHours(24));
    }
}
