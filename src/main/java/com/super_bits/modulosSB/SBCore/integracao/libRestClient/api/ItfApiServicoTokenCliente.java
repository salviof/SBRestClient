/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;

/**
 * @author sfurbino
 * @since 09/12/2019
 * @version 1.0
 */
public interface ItfApiServicoTokenCliente {

    public String getUrlRetornoSucesso();

    public String getUrlSolicitacaoAutenticacao();

    public String getUrlRetornoFalha();

    public InfoTokenOauth2 gerarNovoToken(String pSolicitacao);

    public InfoTokenOauth2 getTokenArmazenadoSistema();

    public InfoTokenOauth2 getTokenArmazenadoUsuario();

}
