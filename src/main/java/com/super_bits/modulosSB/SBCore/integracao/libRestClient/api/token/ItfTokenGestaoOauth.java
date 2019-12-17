/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;

/**
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public interface ItfTokenGestaoOauth extends ItfTokenGestao {

    @Override
    public default boolean isPossuiAutenticacaoDeUsuario() {
        return true;
    }

    public String gerarUrlTokenObterChaveAcesso();

    public String gerarUrlInformadaRetornoSolictacaoSucesso();

    public String gerarNovoToken(String pCodigoSolicitacao);

    public String gerarNovoCodigoSolicitacao(String pRespostaServidorAutenticador);

}
