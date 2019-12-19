/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringSlugs;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;

import java.util.Date;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClientReflexao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import javax.servlet.http.HttpServletRequest;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author SalvioF
 */
public abstract class GestaoTokenOath2 extends GestaoTokenGenerico implements ItfTokenGestaoOauth {

    protected final String chavePublica;
    protected final String chavePrivada;
    protected final String siteCliente;
    protected final String urlServidorApiRest;

    protected String urlSolictacaoToken;
    protected final String urlObterCodigoSolicitacao;
    protected final String urlRetornoReceberCodigoSolicitacao;
    protected final String urlRetornoSucessoObterToken;
    protected String codigoSolicitacao;
    protected InfoTokenOauth2 tokenDeAcesso;

    public GestaoTokenOath2(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpointsClass,
            FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario) {
        super(pClasseEndpointsClass,
                pTipoAgente, pUsuario);
        try {
            chavePrivada = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.CHAVE_PRIVADA);
            chavePublica = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.CHAVE_PUBLICA);
            urlServidorApiRest = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API);
            siteCliente = getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH);
            urlRetornoSucessoObterToken = gerarUrlRetornoSucessoGeracaoTokenDeAcesso();
            urlRetornoReceberCodigoSolicitacao = gerarUrlRetornoReceberCodigoSolicitacao();
            urlObterCodigoSolicitacao = gerarUrlTokenObterCodigoSolicitacao();
            inicioGestaoToken();
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro instanciando " + this.getClass().getSimpleName() + ", é uma prática recomendavel adicionar try cath nos metodos geradores", t);
            throw new UnsupportedOperationException("Erro instanciando " + this.getClass().getSimpleName());
        }
    }

    @Override
    public boolean isTemTokemAtivo() {

        return isPossuiTokenValido();
    }

    @Override
    public String getToken() {

        if (isTemTokemAtivo()) {
            return tokenDeAcesso.getTokenValido();
        }
        return null;

    }

    private void inicioGestaoToken() {

        try {
            loadTokenArmazenado();
        } catch (Throwable t) {

        }
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
    public String getUrlSolictacaoToken() {
        return urlSolictacaoToken;
    }

    @Override
    public String getUrlObterCodigoSolicitacao() {
        return urlObterCodigoSolicitacao;
    }

    @Override
    public String getUrlRetornoReceberCodigoSolicitacao() {
        return urlRetornoReceberCodigoSolicitacao;
    }

    protected abstract String gerarUrlTokenObterChaveAcesso();

    protected abstract String gerarUrlTokenObterCodigoSolicitacao();

    protected String gerarUrlRetornoReceberCodigoSolicitacao() {
        UtilSBApiRestClient.gerarUrlRetornoReceberCodigoSolicitacaoPadrao(this);

        String nomeModuloGestaoAutenticacao = UtilSBApiRestClientReflexao.getNomeClasseImplementacaoGestaoToken(classeFabricaAcessos);
        String nomeParametroRespostaCodigo = "code";
        return getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH)
                + "/solicitacaoAuth2Recept/" + nomeParametroRespostaCodigo + "/"
                + UtilSBCoreStringSlugs.gerarSlugSimples(tipoAgente.getRegistro().getNome())
                + "/" + nomeModuloGestaoAutenticacao + "/";

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

        if (tokenDeAcesso != null) {
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
        urlSolictacaoToken = gerarUrlTokenObterChaveAcesso();
    }

}
