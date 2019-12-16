/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;

import java.util.Date;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author SalvioF
 */
public abstract class GestaoTokenOath2 implements ItfTokenGestaoOauth {

    private final FabTipoAgenteClienteRest tipoCliente;
    private final ConfigModulo confiModulo;
    private final Class<? extends ItfFabricaIntegracaoRest> fabricaOauth;
    private String chavePublica;

    private String chavePrivada;
    private String siteCliente;
    private String siteServidor;
    private String codigoSolicitacao;
    private InfoTokenOauth2 tokenDeAcesso;

    @Override
    public FabTipoAgenteClienteRest getTipoAgente() {
        return tipoCliente;
    }

    public final void atualizarDados() {
        confiModulo.reloadConfiguracoes();
        chavePublica = confiModulo.getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.CHAVE_PUBLICA);
        chavePrivada = confiModulo.getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.CHAVE_PRIVADA);
        siteCliente = confiModulo.getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH);
        siteServidor = confiModulo.getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API);

    }

    public boolean isCodigoSolicitacaoRegistrado() {
        return codigoSolicitacao != null;
    }

    public GestaoTokenOath2(ItfFabricaIntegracaoRest pIntegracaoEndpoint,
            FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario) {
        tipoCliente = pTipoAgente;
        confiModulo = pIntegracaoEndpoint.getConfiguracao();
        fabricaOauth = pIntegracaoEndpoint.getClass();
        atualizarDados();
    }

    public final boolean carregarTokenArmazenado(ItfFabricaIntegracaoRest api) {
        InfoTokenOauth2 tokenencontrado = null;
        switch (tipoCliente) {
            case USUARIO:
                tokenencontrado = api.getApiTokenAcesso().getTokenArmazenadoUsuario();

                break;
            case SISTEMA:
                tokenencontrado = api.getApiTokenAcesso().getTokenArmazenadoSistema();
                break;
            default:
                throw new AssertionError(tipoCliente.name());

        }
        if (tokenencontrado != null) {
            tokenDeAcesso = tokenencontrado;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FabStatusToken getStatusToken() {

        if (getTokenDeAcesso() != null) {
            if (tokenDeAcesso.getDataHoraExpirarToken() == null) {
                return FabStatusToken.ATIVO;
            } else {
                if (tokenDeAcesso.getDataHoraExpirarToken().getTime() < new Date().getTime()) {
                    return FabStatusToken.EXPIRADO;
                } else {
                    return FabStatusToken.ATIVO;
                }
            }
        } else {
            if (codigoSolicitacao != null) {
                return FabStatusToken.SOLICITACAO_TOKEN_EM_ANDAMENTO;
            }
        }

        return FabStatusToken.SEM_TOKEN;
    }

    public boolean isPossuiTokenValido() {

        switch (getStatusToken()) {
            case ATIVO:
                return true;
            default:
                return false;
        }

    }

    public boolean atualizarTokenExpirado() {
        return false;
    }

    public String getUrlAutenticacao(ItfFabricaIntegracaoRest api) {
        return api.getApiTokenAcesso().getUrlSolicitacaoAutenticacao();
    }

    private final ItfFabricaIntegracaoRest getFabricaAcesosMetodosPadrao() {
        return fabricaOauth.getEnumConstants()[0];
    }

    public final void gerarNovoToken(String pCodigoSolicitacao) {
        codigoSolicitacao = pCodigoSolicitacao;
        ItfFabricaIntegracaoRest api = fabricaOauth.getEnumConstants()[0];

        atualizarDados();
        if (isPossuiTokenValido()) {
            return;
        }

        if (codigoSolicitacao != null) {
            tokenDeAcesso = api.getApiTokenAcesso().gerarNovoToken(codigoSolicitacao);
        } else {
            SBCore.getCentralDeMensagens().enviarMsgAvisoAoUsuario("O token não foi encontrado, acesse \n"
                    + api.getApiTokenAcesso().getUrlSolicitacaoAutenticacao() + "\n para obter um token de acesso.");
        }
    }

    public ConfigModulo getConfiModulo() {
        return confiModulo;
    }

    public String getChavePublica() {
        return chavePublica;
    }

    public String getChavePrivada() {
        return chavePrivada;
    }

    public String getSiteCliente() {
        return siteCliente;
    }

    public String getSiteServidor() {
        return siteServidor;
    }

    public String getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public InfoTokenOauth2 getTokenDeAcesso() {
        if (tokenDeAcesso == null) {
            switch (tipoCliente) {
                case USUARIO:
                    tokenDeAcesso = getFabricaAcesosMetodosPadrao().getApiTokenAcesso().getTokenArmazenadoUsuario();
                    break;
                case SISTEMA:
                    tokenDeAcesso = getFabricaAcesosMetodosPadrao().getApiTokenAcesso().getTokenArmazenadoSistema();
                    break;
                default:
                    throw new AssertionError(tipoCliente.name());

            }

        }
        return tokenDeAcesso;
    }

    public void setCodigoSolicitacao(String codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
    }

}
