/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author SalvioF
 */
public abstract class GestaoTokenOath2 extends GestaoTokenOath2Base implements ItfTokenGestaoOauth {

    protected final String chavePublica;
    protected final String chavePrivada;
    protected final String siteCliente;
    protected final String urlServidorApiRest;

    protected String urlObterCodigoSolicitacao;
    protected String urlRetornoReceberCodigoSolicitacao;
    protected String urlRetornoSucessoObterToken;

    public GestaoTokenOath2(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpointsClass,
            FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario) {
        super(pClasseEndpointsClass,
                pTipoAgente, pUsuario, null);
        try {
            urlServidorApiRest = getConfig().getPropriedade(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API);
            chavePrivada = getConfig().getPropriedade(FabPropriedadeModuloIntegracaoOauth.CHAVE_PRIVADA);
            chavePublica = getConfig().getPropriedade(FabPropriedadeModuloIntegracaoOauth.CHAVE_PUBLICA);
            siteCliente = getConfig().getPropriedade(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH);
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
        loadTokenArmazenado();
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

}
