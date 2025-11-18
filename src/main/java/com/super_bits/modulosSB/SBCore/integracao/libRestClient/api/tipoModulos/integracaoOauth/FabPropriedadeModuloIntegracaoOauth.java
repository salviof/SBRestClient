/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;

/**
 *
 * @author SalvioF
 */
public enum FabPropriedadeModuloIntegracaoOauth implements ItfFabConfigModulo, ComoFabrica {

    CHAVE_PUBLICA,
    CHAVE_PRIVADA,
    URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH,
    URL_SERVIDOR_API,
    USUARIO,
    SENHA;

    @Override
    public String getValorPadrao() {
        switch (this) {
            case CHAVE_PUBLICA:
                break;
            case CHAVE_PRIVADA:
                break;
            case URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH:
                break;
            case URL_SERVIDOR_API:
                break;
            case USUARIO:
                break;
            case SENHA:
                break;

            default:
                throw new AssertionError();
        }
        return "não definido";
    }

}
