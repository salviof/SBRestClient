/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;

/**
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public interface ItfTokenGestao {

    public String getToken();

    public ItfTokenDeAcessoExterno getTokenCompleto();

    public ConfigModulo getConfig();

    public boolean isTemTokemAtivo();

    public ItfTokenDeAcessoExterno gerarNovoToken();

    public boolean excluirToken();

    public boolean isPossuiAutenticacaoDeUsuario();

    public FabStatusToken getStatusToken();

    public FabTipoAgenteClienteApi getTipoAgente();

    public boolean validarToken();

    public ItfTokenDeAcessoExterno loadTokenArmazenado();

    public default ItfTokenGestaoOauth getComoGestaoOauth() {
        return (ItfTokenGestaoOauth) this;
    }

    public default ItfGestaoTokenDinamico getComoTokenDinamico() {
        return (ItfGestaoTokenDinamico) this;
    }

}
