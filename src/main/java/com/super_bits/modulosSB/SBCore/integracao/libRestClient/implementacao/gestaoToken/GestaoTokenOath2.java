/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringSlugs;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;

import java.util.Date;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClientOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBIntegracaoClientReflexao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import javax.servlet.http.HttpServletRequest;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;

/**
 *
 * @author SalvioF
 */
public abstract class GestaoTokenOath2 extends GestaoTokenDinamico implements ItfTokenGestaoOauth {

    protected final String chavePublica;
    protected final String chavePrivada;
    protected final String siteCliente;
    protected final String urlServidorApiRest;

    protected String urlObterCodigoSolicitacao;
    protected String urlRetornoReceberCodigoSolicitacao;
    protected String urlRetornoSucessoObterToken;
    protected String codigoSolicitacao;
    protected ChamadaHttpSimples chamadaObterChaveDeAcesso;

    public GestaoTokenOath2(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpointsClass,
            FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario) {
        super(pClasseEndpointsClass,
                pTipoAgente, pUsuario);
        try {
            urlServidorApiRest = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API);
            chavePrivada = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.CHAVE_PRIVADA);
            chavePublica = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.CHAVE_PUBLICA);
            siteCliente = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH);
            loadDadosIniciais();

            if (urlServidorApiRest == null) {
                throw new UnsupportedOperationException("A url do servidor de recepção oauth não foi definida");
            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro instanciando " + this.getClass().getSimpleName() + ", é uma prática recomendavel adicionar try cath nos metodos geradores", t);
            throw new UnsupportedOperationException("Erro instanciando " + this.getClass().getSimpleName());
        }
    }

    protected void loadDadosIniciais() {
        urlRetornoSucessoObterToken = gerarUrlRetornoSucessoGeracaoTokenDeAcesso();
        urlRetornoReceberCodigoSolicitacao = gerarUrlServicoReceberCodigoSolicitacao();
        urlObterCodigoSolicitacao = gerarUrlAutenticaoObterCodigoSolicitacaoToken();
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

    @Override
    public InfoTokenOauth2 getTokenCompleto() {
        return (InfoTokenOauth2) super.getTokenCompleto(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCodigoSolicitacaoRegistrado() {
        return codigoSolicitacao != null;
    }

    @Override
    public String getUrlServidorApiRest() {
        return urlServidorApiRest;
    }

    @Override
    public String getUrlObterCodigoSolicitacao() {
        return urlObterCodigoSolicitacao;
    }

    @Override
    public String getUrlRetornoReceberCodigoSolicitacao() {
        return urlRetornoReceberCodigoSolicitacao;
    }

    protected abstract ChamadaHttpSimples gerarChamadaTokenObterChaveAcesso();

    protected abstract String gerarUrlAutenticaoObterCodigoSolicitacaoToken();

    protected String gerarUrlServicoReceberCodigoSolicitacao() {

        String urlServicoReceberCodigiodeAcesso = UtilSBApiRestClient.gerarUrlServicoReceberCodigoSolicitacaoPadrao(this);

        return urlServicoReceberCodigiodeAcesso;

    }

    protected abstract String gerarUrlRetornoSucessoGeracaoTokenDeAcesso();

    @Override
    public String extrairNovoCodigoSolicitacao(HttpServletRequest pRespostaServidorAutenticador) {

        if (!UtilSBApiRestClient.receberCodigoSolicitacaoOauth(pRespostaServidorAutenticador)) {
            codigoSolicitacao = null;
        }
        return codigoSolicitacao;
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
        return urlServidorApiRest;
    }

    public String getCodigoSolicitacao() {
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
            chamadaObterChaveDeAcesso = gerarChamadaTokenObterChaveAcesso();
            gerarUrlRetornoSucessoGeracaoTokenDeAcesso();

            System.out.println("Gerando token com solicitação" + codigoSolicitacao);

            RespostaWebServiceSimples resp = UtilSBApiRestClient.getRespostaRest(chamadaObterChaveDeAcesso);

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
