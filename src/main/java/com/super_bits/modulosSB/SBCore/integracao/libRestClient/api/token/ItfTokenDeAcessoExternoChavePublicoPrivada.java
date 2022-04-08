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
public interface ItfTokenDeAcessoExternoChavePublicoPrivada extends ItfTokenDeAcessoExterno {

    public default String getChavePublica() {
        return getToken();
    }

    public String getChavePrivada();

}
