package com.juliaosistem.mcs_financial.utils.enums;


public enum MensajesRespuesta {

    CREADO("Creado(a) correctamente"),
    NO_ENCONTRADO("No se encontraron datos"),
    GET("Se obtuvieron datos correctamente"),
    ACTUALIZADO("Actualizado correctamente"),
    ELIMINADO("Eliminado correctamente"),
    FALLO("Algo salió mal"),
    NO_VALID_ID_CARD("EL IdCard no es valido. Un valor valido  tiene que ser un numero con 6 digitos "),
    CREATED_CARD("Tarjeta activada exitosamente"), LOCKED_CARD("Tarjeta bloqueada exitosamente");

    private final String mensaje;

    MensajesRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
