/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.simuladorResposta.ItfSimulacaoRespostaServlet;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClientOauth2;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.lang.reflect.Constructor;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;

/**
 *
 * @author sfurbino
 */
public abstract class GestaoTokenOath2Base extends GestaoTokenDinamico implements ItfTokenGestaoOauth {

    protected String codigoSolicitacao;
    protected ChamadaHttpSimples chamadaObterChaveDeAcesso;

    protected abstract ChamadaHttpSimples gerarChamadaTokenObterChaveAcesso();

    protected abstract String gerarUrlAutenticaoObterCodigoSolicitacaoToken();

    protected abstract String gerarUrlRetornoSucessoGeracaoTokenDeAcesso();

    public GestaoTokenOath2Base(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpointsClass,
            FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, String pIdentificacaoAPi) {
        super(pClasseEndpointsClass, pTipoAgente, pUsuario, pIdentificacaoAPi);
    }

    @Override
    public boolean isTemTokemAtivo() {

        return isPossuiTokenValido();
    }

    @Override
    public String getToken() {

        if (isTemTokemAtivo()) {
            return getTokenCompleto().getTokenValido();
        }
        return null;

    }

    public boolean isPossuiTokenValido() {

        switch (getStatusToken()) {
            case ATIVO:
                return true;
            default:
                return false;
        }

    }

    @Override
    public InfoTokenOauth2 getTokenCompleto() {
        return (InfoTokenOauth2) super.getTokenCompleto(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FabStatusToken getStatusToken() {
        if (getTokenCompleto() == null) {
            return FabStatusToken.SEM_TOKEN;
        }
        if (getTokenCompleto().isTokenValido()) {
            if (getTokenCompleto().getDataHoraExpirarToken() == null) {
                return FabStatusToken.ATIVO;
            } else {
                if (getTokenCompleto().getDataHoraExpirarToken().getTime() < new Date().getTime()) {
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

    @Override
    public boolean isCodigoSolicitacaoRegistrado() {
        return codigoSolicitacao != null;
    }

    protected String gerarUrlServicoReceberCodigoSolicitacao() {

        String urlServicoReceberCodigiodeAcesso = UtilSBApiRestClient.gerarUrlServicoReceberCodigoSolicitacaoPadrao(this);

        return urlServicoReceberCodigiodeAcesso;

    }

    @Override
    public String extrairNovoCodigoSolicitacao(HttpServletRequest pRespostaServidorAutenticador) {

        if (!UtilSBApiRestClient.receberCodigoSolicitacaoOauth(pRespostaServidorAutenticador)) {
            codigoSolicitacao = null;
        }
        return codigoSolicitacao;
    }

    @Override
    public void setCodigoSolicitacao(String codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
        chamadaObterChaveDeAcesso = gerarChamadaTokenObterChaveAcesso();
    }

    @Override
    public InfoTokenOauth2 gerarNovoToken() {
        try {
            if (codigoSolicitacao == null) {
                UtilSBApiRestClientOauth2.solicitarAutenticacaoExterna(this);
                throw new UnsupportedOperationException("O código de solitação não foi encontrado");
            }
            if (chamadaObterChaveDeAcesso == null) {
                chamadaObterChaveDeAcesso = gerarChamadaTokenObterChaveAcesso();
            }
            gerarUrlRetornoSucessoGeracaoTokenDeAcesso();

            System.out.println("Gerando token com solicitação" + codigoSolicitacao);
            RespostaWebServiceSimples resp = null;
            if (SBCore.isEmModoDesenvolvimento() && SBCore.getNomeProjeto().equals("intERPRestful")) {
                Class simulacaoPostTrasferenciaToken = this.getClass().getClassLoader().loadClass("br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopePostToken");
                Constructor constructor = simulacaoPostTrasferenciaToken.getConstructor(ChamadaHttpSimples.class);
                ItfSimulacaoRespostaServlet simulacao = (ItfSimulacaoRespostaServlet) constructor.newInstance(chamadaObterChaveDeAcesso);
                resp = simulacao.getRespostaWS();
                System.out.println(simulacaoPostTrasferenciaToken.getClass().getSimpleName());
                System.out.println("");
            } else {
                resp = UtilSBApiRestClient.getRespostaRest(chamadaObterChaveDeAcesso);
            }
            if (resp == null) {
                return null;
            }
            if (resp.isSucesso()) {
                JSONObject respostaJson = resp.getRespostaComoObjetoJson();

                armazenarRespostaToken(respostaJson.toJSONString());
                loadTokenArmazenado();
                return getTokenCompleto();
            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro " + t.getMessage(), t);
        }
        codigoSolicitacao = null;

        return null;

    }
}
