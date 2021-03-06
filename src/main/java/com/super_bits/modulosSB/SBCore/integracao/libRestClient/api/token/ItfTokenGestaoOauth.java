/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public interface ItfTokenGestaoOauth extends ItfGestaoTokenDinamico {

    @Override
    public default boolean isPossuiAutenticacaoDeUsuario() {
        return true;
    }

    public boolean isCodigoSolicitacaoRegistrado();

    public String getUrlServidorApiRest();

    public String getUrlObterCodigoSolicitacao();

    public String getUrlRetornoReceberCodigoSolicitacao();

    public String extrairNovoCodigoSolicitacao(HttpServletRequest pRespostaServidorAutenticador);

    public void setCodigoSolicitacao(String pCodigo);

}
