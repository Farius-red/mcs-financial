package com.juliaosistem.mcs_financial.utils.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseType {

    CREATED(1, MensajesRespuesta.CREADO.getMensaje(), true, HttpStatus.CREATED),
    UPDATED(2, MensajesRespuesta.ACTUALIZADO.getMensaje(), true, HttpStatus.OK),
    FALLO(3, MensajesRespuesta.FALLO.getMensaje(), false, HttpStatus.INTERNAL_SERVER_ERROR),
    GET(10,MensajesRespuesta.GET.getMensaje(),true,HttpStatus.OK),
    NO_ENCONTRADO(11, MensajesRespuesta.NO_ENCONTRADO.getMensaje(), false, HttpStatus.NOT_FOUND),
    NO_VALID_ID_CARD(12, MensajesRespuesta.NO_VALID_ID_CARD.getMensaje(), false, HttpStatus.BAD_REQUEST),
    CREATED_CARD(13, MensajesRespuesta.CREATED_CARD.getMensaje(), true, HttpStatus.CREATED),
    LOCKED_CARD(14,MensajesRespuesta.LOCKED_CARD.getMensaje() ,true ,HttpStatus.OK ),
    RELOAD_BALANCE(15,MensajesRespuesta.RELOAD_BALANCE.getMensaje() ,true ,HttpStatus.OK ),
    BUY_SUCCESS(16,MensajesRespuesta.BUY_SUCCES.getMensaje() ,true ,HttpStatus.OK ),
    CARD_NO_FOUND(17,MensajesRespuesta.CARD_NO_FOUND.getMensaje() ,false ,HttpStatus.NOT_FOUND ),
    TRANSACTION_FAIL(18,MensajesRespuesta.TRANSACTION_FAIL.getMensaje() ,false , HttpStatus.OK),
    TRANSACTION_SUCCESS_NULL(19,MensajesRespuesta.TRANSACTION_SUCCESS_NULL.getMensaje() ,true ,HttpStatus.OK ),
    CARD_DONT_HAVE_FOUNDS(20,MensajesRespuesta.CARD_DONT_HAVE_FOUNDS.getMensaje() ,false ,HttpStatus.OK ),
    CARD_BLOCK(21,MensajesRespuesta.CARD_BLOCK.getMensaje() ,false ,HttpStatus.OK );


    private final int code;

    private final String message;
    private final boolean isRta;
    private final HttpStatus httpStatus;

    ResponseType(int code, String message, boolean isRta, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.isRta = isRta;
        this.httpStatus = httpStatus;
    }



    public boolean isRta() {
        return isRta;
    }


    public static ResponseType fromCode(int code) {
        for (ResponseType responseType : ResponseType.values()) {
            if (responseType.code == code) {
                return responseType;
            }
        }
        return null;
    }
}
