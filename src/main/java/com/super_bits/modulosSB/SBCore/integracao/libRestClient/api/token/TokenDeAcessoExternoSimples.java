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
public class TokenDeAcessoExternoSimples implements ItfTokenDeAcessoExterno {

    public TokenDeAcessoExternoSimples(String pToken) {
        token = pToken;
    }

    private final String token;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public boolean isTokenValido() {
        return token == null;
    }

}
