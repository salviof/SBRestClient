/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBIntegracaoClientReflexao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiCliente;

/**
 *
 * @author sfurbino
 */
public interface ComoFabricaIntegracaoApi {

    public default ItfAcaoApiCliente getAcao(Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteApi.SISTEMA, null, parametros);
    }

    public default ItfAcaoApiCliente getAcao(FabTipoAgenteClienteApi pTipo, ComoUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, pTipo, pUsuario, parametros);
    }

    public default ItfAcaoApiCliente getAcao(ComoUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteApi.USUARIO, pUsuario, parametros);
    }

    public default ItfAcaoApiCliente getAcaoUsuarioLogado(ComoUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteApi.USUARIO, SBCore.getUsuarioLogado(), parametros);
    }

    public default ItfTokenGestao getGestaoToken() {
        ItfTokenGestao tokenGestao = MapaTokensGerenciados.getAutenticadorSistema(this.getClasseGestaoOauth());

        if (tokenGestao == null) {
            tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(this, FabTipoAgenteClienteApi.SISTEMA, null, null);
            MapaTokensGerenciados.registrarAutenticador(tokenGestao);
        }
        return MapaTokensGerenciados.getAutenticadorSistema(this);

    }

    public default ItfTokenGestao getGestaoToken(String pIdentificadorApi) {
        ItfTokenGestao tokenGestao = MapaTokensGerenciados.getAutenticadorSistema(this.getClasseGestaoOauth());

        if (tokenGestao == null) {
            tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(this, FabTipoAgenteClienteApi.SISTEMA, null, pIdentificadorApi);
            MapaTokensGerenciados.registrarAutenticador(tokenGestao);
        }
        return MapaTokensGerenciados.getAutenticadorSistema(this);

    }

    public default ItfTokenGestao getGestaoToken(ComoUsuario pUsuario, String pIdentificadorApi) {
        ItfTokenGestao tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(getClasseGestaoOauth(), pUsuario, pIdentificadorApi);
        if (tokenGestao == null) {
            tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(this, FabTipoAgenteClienteApi.USUARIO, pUsuario, pIdentificadorApi);
            MapaTokensGerenciados.registrarAutenticadorUsuario(tokenGestao, pUsuario, pIdentificadorApi);
        }

        return MapaTokensGerenciados.getAutenticadorUsuario(tokenGestao.getClass(), pUsuario, pIdentificadorApi);
    }

    public default ItfTokenGestao getGestaoToken(ComoUsuario pUsuario) {
        ItfTokenGestao tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(getClasseGestaoOauth(), pUsuario);
        if (tokenGestao == null) {
            tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(this, FabTipoAgenteClienteApi.USUARIO, pUsuario, null);
            MapaTokensGerenciados.registrarAutenticadorUsuario(tokenGestao, pUsuario);
        }
        return MapaTokensGerenciados.getAutenticadorUsuario(this, pUsuario);
    }

    public default Class<? extends ItfTokenGestao> getClasseGestaoOauth() {
        return UtilSBIntegracaoClientReflexao.getClasseToken(this);
    }

    public default ConfigModulo getConfiguracao() {
        return UtilSBIntegracaoClientReflexao.getConfigmodulo(this);

    }

}
