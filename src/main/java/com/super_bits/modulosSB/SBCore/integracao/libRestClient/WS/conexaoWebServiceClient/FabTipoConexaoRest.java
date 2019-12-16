/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient;

/**
 *
 * @author SalvioF
 */
public enum FabTipoConexaoRest {

    POST, GET, PUT, PATCH, DELET, OPTIONS, INDETERMINADO;

    public String getMetodoRequest() {
        switch (this) {
            case POST:
                return "POST";

            case GET:
            case INDETERMINADO:
                return "GET";

            case PUT:
                return "PUT";
            case PATCH:
                return "PATCH";
            case DELET:
                return "DELET";
            case OPTIONS:
                return "OPTIONS";

            default:
                throw new AssertionError(this.name());

        }
    }
}
