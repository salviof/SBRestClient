/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
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
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.InfoPropriedadeConfigRestIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author desenvolvedor
 */
public abstract class AcaoApiIntegracaoRestAbstratoBasico extends AcaoApiIntegracaoAbstratoBasico implements ItfAcaoApiRest {

    private ConsumoWSExecucao consumoWS;

    private String corpoRequisicaoGerado;
    private byte[] corpoBytesRequisicaoGerado;
    private String urlRequisicaoGerada;

    private FabTipoConexaoRest tipoRequisicao;
    private Map<String, String> cabecalhoGerado;
    private boolean postarInformacoes;

    public ItfUsuario usuario;
    private String token;
    protected InfoConsumoRestService infoRest;
    private String urlServidor;

    public AcaoApiIntegracaoRestAbstratoBasico(ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        this(null, pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
    }

    public AcaoApiIntegracaoRestAbstratoBasico(String pTipoAplicacao, ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        super(pTipoAplicacao, pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
        try {

            infoRest = UtilSBApiRestClient.getInformacoesConsumoRest(pIntegracaoEndpoint);

            try {

                executarAcao();
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando ação de integração Rest em:" + pIntegracaoEndpoint, t);
            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando ação de integração Rest em:" + pIntegracaoEndpoint, t);
        }

    }

    public ConfigModulo getConfiguracao() {
        return getFabricaApi().getConfiguracao();
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
                    List<String> parametrosObrigatorios = new ArrayList<>();
                    List<String> parametrosOpcionais = new ArrayList<>();
                    parametrosRelatadosUrl.stream().filter(pr -> pr.length() > 1).forEach(parametrosOpcionais::add);
                    parametrosRelatadosUrl.stream().filter(pr -> pr.length() == 1).forEach(parametrosObrigatorios::add);
                    if (parametrosObrigatorios.size() > getParametros().length) {
                        throw new UnsupportedOperationException("São experados" + parametrosRelatadosUrl.size() + " parametros");
                    }
                    for (String p : parametrosRelatadosUrl) {
                        boolean opcional = p.length() > 1;
                        int idParametro = Integer.valueOf(p);
                        if (!opcional) {
                            String valor = String.valueOf(getParametros()[idParametro]);
                            String parametroEncode = URLEncoder.encode(valor, StandardCharsets.UTF_8.toString());
                            urlReq = urlReq.replace("{" + p + "}", parametroEncode);
                        } else {
                            if (parametros.size() <= idParametro) {
                                urlReq = urlReq.replace("{" + p + "}", "");
                            } else {
                                String valor = String.valueOf(getParametros()[idParametro]);
                                String parametroEncode = URLEncoder.encode(valor, StandardCharsets.UTF_8.toString());
                                urlReq = urlReq.replace("{" + p + "}", parametroEncode);
                            }
                        }
                    }

                }
                urlReq = urlReq.replace("////", "");
                return urlReq;
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando url de solicitação em " + this.getClass().getSimpleName(), t);
                return (getUrlServidor() + infoRest.getPachServico());
            }

        }
    }

    @Override
    public String gerarCorpoRequisicao() {
        switch (tipoRequisicao) {
            case POST:
            case PUT:

                throw new UnsupportedOperationException("Implemente o corpo da requisição para a classe" + this.getClass().getSimpleName());

            default:

        }
        return "";
    }

    @Override
    public byte[] gerarBytesCorpoRequisicao() {
        return null;
    }

    private boolean defineRequisicaoPostaInformacoes() {
        switch (tipoRequisicao) {
            case POST:
            case PUT:
            case DELETE:
                return true;

            case GET:
                break;

            case PATCH:
                break;
            case INDETERMINADO:

                break;
            case OPTIONS:
                return false;

            default:
                throw new AssertionError(infoRest.tipoConexao().name());

        }
        return false;

    }

    @Override
    public Map<String, String> gerarCabecalho() {

        return UtilSBIntegracaoClientReflexao.getHeaderPadrao(getFabricaApi(), this).getHeaderPadrao();
    }

    @Override
    public FabTipoConexaoRest gerarTipoRequisicao() {

        if (getFabricaApi().getInformacoesConsumo().tipoConexao().equals(FabTipoConexaoRest.INDETERMINADO)) {
            if (this.toString().contains("CTR")) {
                return FabTipoConexaoRest.PUT;
            }
            if (this.toString().contains("SELECAO")) {
                return FabTipoConexaoRest.PATCH;
            }

            return FabTipoConexaoRest.GET;

        } else {
            return getFabricaApi().getInformacoesConsumo().tipoConexao();
        }
    }

    @Override
    protected void executarAcao() {
        if (getTokenGestao() == null || !getTokenGestao().isTemTokemAtivo()) {
            try {
                if (getTokenGestao().isTokenTipoGestaoOauth()) {
                    if (getTokenGestao().getComoGestaoOauth().getTipoAgente().equals(FabTipoAgenteClienteApi.SISTEMA)) {
                        try {
                            if (!getTokenGestao().getComoGestaoOauth().isCodigoSolicitacaoRegistrado()) {
                                UtilSBApiRestClientOauth2.solicitarAutenticacaoExterna(getTokenGestao().getComoGestaoOauth());
                            }
                            getTokenGestao().gerarNovoToken();
                        } catch (Throwable t) {
                            System.out.println("Fala solicitando código de autenticação");
                        }

                    }
                } else {
                    getTokenGestao().gerarNovoToken();
                }// pode gerar situalções de to many conections, a gestão do token deve ser feita por controles externos
            } catch (Throwable t) {
                gerarResposta(null);
                resposta = new RespostaWebServiceRestIntegracao(t.getMessage(), -1, "Erro indeterminado gerando token de acesso à api." + t.getMessage());
                return;
            }
        }
        if (!getTokenGestao().isTemTokemAtivo()) {
            gerarResposta(null);
        } else {
            urlRequisicaoGerada = gerarUrlRequisicao();
            tipoRequisicao = gerarTipoRequisicao();
            postarInformacoes = defineRequisicaoPostaInformacoes();
            corpoRequisicaoGerado = gerarCorpoRequisicao();
            if (infoRest.corpoRequisicaoEmBytes()) {
                corpoBytesRequisicaoGerado = gerarBytesCorpoRequisicao();
            }
            cabecalhoGerado = gerarCabecalho();

            consumoWS = new ConsumoWSExecucao() {

                @Override
                public RespostaWebServiceSimples efetuarConexao() {
                    if (!infoRest.corpoRequisicaoEmBytes()) {
                        return gerarRespostaTratamentoFino(UtilSBApiRestClient.getRespostaRest(
                                urlRequisicaoGerada, tipoRequisicao, postarInformacoes, cabecalhoGerado, corpoRequisicaoGerado, infoRest.aceitarCertificadoDeHostNaoConfiavel()));
                    } else {
                        return gerarRespostaTratamentoFino(UtilSBApiRestClient.getRespostaRest(
                                urlRequisicaoGerada, tipoRequisicao, postarInformacoes, cabecalhoGerado,
                                null,
                                corpoBytesRequisicaoGerado,
                                infoRest.aceitarCertificadoDeHostNaoConfiavel()));
                    }

                }

            };

            gerarResposta(consumoWS);
        }
    }

    protected RespostaWebServiceSimples gerarRespostaTratamentoFino(RespostaWebServiceSimples pRespostaWSSemTratamento) {
        try {
            if (pRespostaWSSemTratamento == null) {
                throw new UnsupportedOperationException("Nenhuma resposta foi gerada");
            }
        } catch (Throwable t) {

        }

        return pRespostaWSSemTratamento;
    }

    @Override
    public boolean isMetodoEnviaDados() {
        return ((tipoRequisicao.equals(FabTipoConexaoRest.PUT)) || (tipoRequisicao.equals(FabTipoConexaoRest.POST)));
    }

    protected final String getCorpoRequisicao() {
        return corpoRequisicaoGerado;
    }

    @Override
    public void gerarResposta(ConsumoWSExecucao pConsumoRest) {
        if (pConsumoRest == null) {
            pConsumoRest = consumoWS;
        }
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
                    String retorno = resp.getResposta();
                    if (!retorno.contains("OK")) {
                        resp.addErro("Resposta do ping diferente do esperado");
                    }
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

                        } catch (Throwable t) {
                            System.out.println("Aguardando a validação em 10 segundos");
                        }
                    }
                }

            } else {
                resposta = new RespostaWebServiceRestIntegracao(token, 400, "A chamada não foi realizada pois não foi possível obter o Token de acesso");
            }
        } else {
            pConsumoRest.start();

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
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Não foi encontrado nenhuma propriedade anotada com " + FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API.toString() + " utilize a anotação " + InfoPropriedadeConfigRestIntegracao.class.getSimpleName() + "para especificar o nome da propriedade de ambiente, no enum de configuração, ou sobrescreva o metodo getUrlServidor da classe " + AcaoApiIntegracaoRestAbstratoBasico.class.getSimpleName(), t);
            }
        }
        return urlServidor;
    }

    @Override
    public ItfRespostaWebServiceSimples getResposta() {
        return (ItfRespostaWebServiceSimples) resposta;
    }

    public int getQuantidadeParametrosEnviados() {
        if (parametros == null) {
            return 0;

        } else {
            return parametros.size();
        }

    }

    @Override
    public ItfFabricaIntegracaoRest getFabricaApi() {
        return (ItfFabricaIntegracaoRest) super.getFabricaApi();
    }

}
