/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.ItfApiServicoTokenCliente;

/**
 *
 * @author sfurbino
 * @since 09/12/2019
 * @version 1.0
 */
public class ServicoTokenClienteRestIntegracao implements ItfApiServicoTokenCliente {

    public ServicoTokenClienteRestIntegracao(ItfConfigModulo pConfig) {

    }

    @Override
    public String getUrlRetornoSucesso() {
        throw new UnsupportedOperationException("O METODO AINDA N\u00c3O FOI IMPLEMENTADO.");
    }

    @Override
    public String getUrlSolicitacaoAutenticacao() {
        throw new UnsupportedOperationException("O METODO AINDA N\u00c3O FOI IMPLEMENTADO.");
    }

    @Override
    public String getUrlRetornoFalha() {
        throw new UnsupportedOperationException("O METODO AINDA N\u00c3O FOI IMPLEMENTADO.");
    }

    @Override
    public InfoTokenOauth2 gerarNovoToken(String pSolicitacao) {
        throw new UnsupportedOperationException("O METODO AINDA N\u00c3O FOI IMPLEMENTADO.");
    }

    @Override
    public InfoTokenOauth2 getTokenArmazenadoSistema() {
        throw new UnsupportedOperationException("O METODO AINDA N\u00c3O FOI IMPLEMENTADO.");
    }

    @Override
    public InfoTokenOauth2 getTokenArmazenadoUsuario() {
        throw new UnsupportedOperationException("O METODO AINDA N\u00c3O FOI IMPLEMENTADO.");
    }

}
