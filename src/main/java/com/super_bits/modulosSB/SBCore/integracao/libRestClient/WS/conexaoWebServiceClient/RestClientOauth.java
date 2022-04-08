/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient;

/**
 *
 * @author salviofurbino
 * @since 07/06/2019
 * @version 1.0
 */
@Deprecated
public class RestClientOauth {

//    private final ConsumoWSExecucao consumoWS;
    private String tokenAcessoValido;

    public RestClientOauth(String pTokenValido) {
        tokenAcessoValido = pTokenValido;

    }

    private void executarMetodo(String pString) {

    }
}
