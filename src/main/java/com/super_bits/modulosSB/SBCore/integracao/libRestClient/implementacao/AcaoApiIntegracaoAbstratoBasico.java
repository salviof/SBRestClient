/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreClienteRest;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringBuscaTrecho;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreWebBrowser;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.RespostaWebServiceRestIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ConsumoWSExecucao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.InfoPropriedadeConfigRestIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author desenvolvedor
 */
public abstract class AcaoApiIntegracaoAbstratoBasico implements ItfAcaoApiRest {

    private ConsumoWSExecucao consumoWS;
    protected ItfFabricaIntegracaoRest fabricaIntegracao;
    protected InfoConsumoRestService infoRest;
    private String corpoRequisicaoGerado;
    private String urlRequisicaoGerada;
    private ItfTokenGestao tokenGestao;
    private FabTipoConexaoRest tipoRequisicao;
    private Map<String, String> cabecalhoGerado;
    private boolean postarInformacoes;
    protected RespostaWebServiceRestIntegracao resposta;
    private ItfUsuario usuario;
    private String token;
    protected Object[] parametros;
    private FabTipoAgenteClienteRest tipoAgente;
    private String urlServidor;

    public AcaoApiIntegracaoAbstratoBasico(ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        this.fabricaIntegracao = pIntegracaoEndpoint;
        infoRest = UtilSBApiRestClient.getInformacoesConsumoRest(pIntegracaoEndpoint);
        if (pParametros != null) {
            parametros = (Object[]) pParametros[0];
        } else {
            parametros = null;
        }

        usuario = pUsuario;
        if (usuario == null) {
            tipoAgente = FabTipoAgenteClienteRest.SISTEMA;
        } else {
            tipoAgente = pTipoAgente;
        }
        try {

            executarAcao();
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando ação de integração Rest em:" + pIntegracaoEndpoint, t);
        }

    }

    public ConfigModulo getConfiguracao() {
        return fabricaIntegracao.getConfiguracao();
    }

    @Override
    public String gerarUrlRequisicao() {

        if (infoRest == null) {
            return getUrlServidor();
        } else {
            try {
                String urlReq = getUrlServidor() + infoRest.getPachServico();

                if (urlReq.contains("{")) {
                    List<String> parametrosRelatadosUrl = UtilSBCoreStringBuscaTrecho.getPartesEntreChaves(urlReq);
                    for (String p : parametrosRelatadosUrl) {
                        String valor = (String) parametros[Integer.valueOf(p)];
                        urlReq = urlReq.replace("{" + p + "}", valor);
                    }

                }
                return urlReq;
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando url de solicitação em " + this.getClass().getSimpleName(), t);
                return (getUrlServidor() + infoRest.getPachServico());
            }

        }
    }

    @Override
    public String gerarCorpoRequisicao() {
        return "";
    }

    private boolean defineRequisicaoPostaInformacoes() {
        switch (tipoRequisicao) {
            case POST:
            case PUT:
            case DELET:
                return true;

            case GET:
                break;

            case PATCH:
                break;
            case INDETERMINADO:

                break;

            default:
                throw new AssertionError(infoRest.tipoConexao().name());

        }
        return false;

    }

    @Override
    public Map<String, String> gerarCabecalho() {

        return UtilSBApiRestClientReflexao.getHeaderPadrao(fabricaIntegracao, this).getHeaderPadrao();
    }

    @Override
    public FabTipoConexaoRest gerarTipoRequisicao() {

        if (fabricaIntegracao.getInformacoesConsumo().tipoConexao().equals(FabTipoConexaoRest.INDETERMINADO)) {
            if (this.toString().contains("CTR")) {
                return FabTipoConexaoRest.PUT;
            }
            if (this.toString().contains("SELECAO")) {
                return FabTipoConexaoRest.PATCH;
            }

            return FabTipoConexaoRest.GET;

        } else {
            return fabricaIntegracao.getInformacoesConsumo().tipoConexao();
        }
    }

    protected void executarAcao() {
        if (!getTokenGestao().isTemTokemAtivo()) {
            gerarResposta(null);
        } else {
            urlRequisicaoGerada = gerarUrlRequisicao();
            tipoRequisicao = gerarTipoRequisicao();
            postarInformacoes = defineRequisicaoPostaInformacoes();
            corpoRequisicaoGerado = gerarCorpoRequisicao();
            cabecalhoGerado = gerarCabecalho();

            consumoWS = new ConsumoWSExecucao() {

                @Override
                public RespostaWebServiceSimples efetuarConexao() {
                    return UtilSBApiRestClient.getRespostaRest(
                            urlRequisicaoGerada, tipoRequisicao, postarInformacoes, cabecalhoGerado, corpoRequisicaoGerado);

                }

            };

            gerarResposta(consumoWS);
        }
    }

    @Override
    public boolean isMetodoEnviaDados() {
        return ((tipoRequisicao.equals(FabTipoConexaoRest.PUT)) || (tipoRequisicao.equals(FabTipoConexaoRest.POST)));
    }

    @Override
    public void gerarResposta(ConsumoWSExecucao pConsumoRest) {
        if (!getTokenGestao().isTemTokemAtivo()) {
            if (getTokenGestao() instanceof ItfTokenGestaoOauth) {
                ItfTokenGestaoOauth tokenOauth = (ItfTokenGestaoOauth) getTokenGestao();
                resposta = new RespostaWebServiceRestIntegracao(token, 400, "A chamada não foi realizada pois o Token não está ativo, acesse: " + tokenOauth.getUrlObterCodigoSolicitacao());

                if (!SBCore.isEmModoDesenvolvimento()) {
                    SBCore.enviarAvisoAoUsuario("O acesso ao serviço precisa ser renovado em " + getTokenGestao().getComoGestaoOauth().getUrlObterCodigoSolicitacao());

                }

                if (SBCore.isEmModoDesenvolvimento()) {
                    SBCore.soutInfoDebug("Modo em desenvolvimento detectado");
                    Map<String, String> cabecalhos = new HashMap<>();
                    cabecalhos.put("OPERACAO", "PING");
                    String urlservicoRetornoCodigoSolicitacao = getTokenGestao().getComoGestaoOauth().getUrlRetornoReceberCodigoSolicitacao() + "/" + UtilSBApiRestClientOauth2.PATH_TESTE_DE_VIDA_SERVICO_RECEPCAO;
                    RespostaWebServiceSimples resp = UtilSBApiRestClient.getRespostaRest(urlservicoRetornoCodigoSolicitacao, FabTipoConexaoRest.GET,
                            false, cabecalhos, "Apenas teste");
                    if (resp == null || !resp.isSucesso()) {
                        try {
                            throw new UnsupportedOperationException("O SERVIÇO DE RECEPÇÃO DE CÓDIGOS PARA OBENÇÃO DE CÓDIGO DE ACESSO NÃO ESTÁ ATIVO, ANTES DE INICIAR O TESTE INICIE O SERVIÇO, QUE SE ENCONTRA NO PACOTE DE TESTES DE API, utilize  ServicoRecepcaoOauthTestes.iniciarServico()");

                        } catch (Throwable t) {
                            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, t.getMessage(), t);
                        }
                    } else {
                        SBCore.enviarMensagemUsuario("Você precisa autenticar seu usuário no servidor, para isso funcionar, além das chaves pública e privadas configuradas, o endereço a seguir precisa estar cadastrado como callbak do oauth:" + getTokenGestao().getComoGestaoOauth().getUrlRetornoReceberCodigoSolicitacao(), FabMensagens.AVISO);
                        String urlObterCodigoParaAutenticacao = getTokenGestao().getComoGestaoOauth().getUrlObterCodigoSolicitacao();
                        SBCore.enviarMensagemUsuario("10 segundos para você autenticar o usuário na janela que abriu com o endereço: " + urlObterCodigoParaAutenticacao, FabMensagens.AVISO);
                        UtilSBCoreWebBrowser.abrirLinkEmBrownser(urlObterCodigoParaAutenticacao);
                        try {
                            Thread.sleep(10000);
                            Thread.sleep(10000000);
                        } catch (Throwable t) {
                            System.out.println("Aguardando a validação em 10 segundos");
                        }
                    }
                }

            } else {
                resposta = new RespostaWebServiceRestIntegracao(token, 400, "A chamada não foi realizada pois o Token não está ativo");
            }
        } else {
            consumoWS.start();
            resposta = pConsumoRest.getResposta();
        }
    }

    @Override
    public String getUrlServidor() {

        if (urlServidor == null) {
            ConfigModulo config = getConfiguracao();
            try {
                urlServidor = config.getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API);
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Não foi encontrado nenhuma propriedade anotada com " + FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API.toString() + " utilize a anotação " + InfoPropriedadeConfigRestIntegracao.class.getSimpleName() + "para especificar o nome da propriedade de ambiente, no enum de configuração, ou sobrescreva o metodo getUrlServidor da classe " + AcaoApiIntegracaoAbstratoBasico.class.getSimpleName(), t);
            }
        }
        return urlServidor;
    }

    @Override
    public RespostaWebServiceSimples getResposta() {
        return resposta;
    }

    @Override
    public ItfTokenGestao getTokenGestao() {
        if (tokenGestao == null) {
            switch (tipoAgente) {
                case USUARIO:
                    tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(fabricaIntegracao.getClasseGestaoOauth(), usuario);
                    if (tokenGestao == null) {
                        tokenGestao = UtilSBApiRestClientReflexao.getNovaInstanciaGestaoAutenticador(fabricaIntegracao, tipoAgente, usuario);
                        MapaTokensGerenciados.registrarAutenticadorUsuario(tokenGestao, usuario);
                    }
                    break;
                case SISTEMA:
                    tokenGestao = MapaTokensGerenciados.getAutenticadorSistema(fabricaIntegracao);
                    if (tokenGestao == null) {
                        tokenGestao = UtilSBApiRestClientReflexao.getNovaInstanciaGestaoAutenticador(fabricaIntegracao, tipoAgente, usuario);
                        MapaTokensGerenciados.registrarAutenticador(tokenGestao);
                    }
                    break;
                default:
                    throw new AssertionError(tipoAgente.name());

            }
        }

        return tokenGestao;
    }

    public int getQuantidadeParametrosEnviados() {
        if (parametros == null) {
            return 0;

        } else {
            return parametros.length;
        }

    }
}
