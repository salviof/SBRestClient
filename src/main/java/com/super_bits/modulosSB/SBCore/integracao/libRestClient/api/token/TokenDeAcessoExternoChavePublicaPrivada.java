/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

/**
 *
 * @author sfurbino
 */
public class TokenDeAcessoExternoChavePublicaPrivada extends TokenDeAcessoExternoSimples
        implements ItfTokenDeAcessoExternoChavePublicoPrivada {

    private final String chavePrivada;

    public TokenDeAcessoExternoChavePublicaPrivada(String pChavePublica, String pChavePrivada) {
        super(pChavePublica);
        chavePrivada = pChavePrivada;
    }

    @Override
    public String getChavePrivada() {
        return chavePrivada;
    }

}
