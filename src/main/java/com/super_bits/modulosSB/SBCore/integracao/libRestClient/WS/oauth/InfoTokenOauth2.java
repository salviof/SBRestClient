/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth;

import java.util.Date;

/**
 *
 * @author SalvioF
 */
public class InfoTokenOauth2 {

    private String chavePublica;
    private String chavePrivada;
    private String tokenValido;
    private String tokenRefresh;
    private Date dataHoraExpirarToken;

    public InfoTokenOauth2(String pToken) {
        tokenValido = pToken;
    }

    public String getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getChavePrivada() {
        return chavePrivada;
    }

    public void setChavePrivada(String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }

    public String getTokenValido() {
        return tokenValido;
    }

    public void setTokenValido(String tokenValido) {
        this.tokenValido = tokenValido;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    public Date getDataHoraExpirarToken() {
        return dataHoraExpirarToken;
    }

    public void setDataHoraExpirarToken(Date dataHoraExpirarToken) {
        this.dataHoraExpirarToken = dataHoraExpirarToken;
    }

}
