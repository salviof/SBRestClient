/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBIntegracaoClientReflexao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 *
 * TODO Avaliar tifificação utilizando o tipo de TOken
 *
 * @author desenvolvedorninja01
 * @since 13/12/2019
 * @version 1.0
 */
public abstract class GestaoTokenGenerico implements ItfTokenGestao {

    protected final FabTipoAgenteClienteApi tipoAgente;
    protected final ItfUsuario usuario;
    protected final Class<? extends ItfFabricaIntegracaoRest> classeFabricaAcessos;
    protected final ItfConfigModulo configuracoesAmbiente;
    private ItfTokenDeAcessoExterno token;

    public GestaoTokenGenerico(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpoints,
            FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario) {
        tipoAgente = pTipoAgente;
        usuario = pUsuario;
        configuracoesAmbiente = UtilSBIntegracaoClientReflexao.getConfigmodulo(pClasseEndpoints);
        classeFabricaAcessos = pClasseEndpoints;
        token = loadTokenArmazenado();

    }

    @Override
    public boolean isTemTokemAtivo() {
        return token != null;
    }

    @Override
    public ItfTokenDeAcessoExterno getTokenCompleto() {
        return token;
    }

    @Override
    public FabTipoAgenteClienteApi getTipoAgente() {
        return tipoAgente;
    }

    @Override
    public final ConfigModulo getConfig() {
        return UtilSBIntegracaoClientReflexao.getConfigmodulo(classeFabricaAcessos);
    }

    @Override
    public String getToken() {

        return token.getToken();
    }

    @Override
    public boolean isPossuiAutenticacaoDeUsuario() {
        return false;
    }

    @Override
    public FabStatusToken getStatusToken() {

        if (token != null) {
            return FabStatusToken.ATIVO;
        }
        return FabStatusToken.SEM_TOKEN;
    }

    @Override
    public boolean excluirToken() {
        token = null;
        switch (tipoAgente) {
            case USUARIO:
                getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno(usuario.getEmail(), "");
                break;
            case SISTEMA:
                getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno("tokensistema", "");
                break;
            default:
                throw new AssertionError(tipoAgente.name());

        }
        return true;
    }

    protected void setToken(ItfTokenDeAcessoExterno pToken) {
        token = pToken;
    }

    public ItfUsuario getUsuario() {
        return usuario;
    }

}
