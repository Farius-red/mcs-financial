package com.juliaosistem.mcs_financial.utils.enums;


public enum MensajesRespuesta {

    CREADO("Creado(a) correctamente"),
    NO_ENCONTRADO("No se encontraron datos"),
    GET("Se obtuvieron datos correctamente"),
    ACTUALIZADO("Actualizado correctamente"),
    ELIMINADO("Eliminado correctamente"),
    FALLO("Algo salió mal"),
    NO_VALID_ID_CARD("EL IdCard no es valido. Un valor valido  tiene que ser un numero con 6 digitos "),
    CREATED_CARD("Tarjeta activada exitosamente"),
    LOCKED_CARD("Tarjeta bloqueada exitosamente"),
    RELOAD_BALANCE("Saldo recargado exitosamente"),
    BUY_SUCCES("Compra realizada exitosamente"),
    CARD_NO_FOUND("Tarjeta no encontrada"),
    TRANSACTION_FAIL("La transacción a anular no debe ser mayor a 24 horas."),
    TRANSACTION_SUCCESS_NULL("Transacción anulada exitosamente"),
    CARD_DONT_HAVE_FOUNDS("Fondos Insuficientes"),
    CARD_BLOCK("Su Tarjeta esta bloqueada");

    private final String mensaje;

    MensajesRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
