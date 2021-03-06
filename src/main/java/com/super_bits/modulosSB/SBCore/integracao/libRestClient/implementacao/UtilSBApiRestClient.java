/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringListas;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringSlugs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servletRecepcaoTokenOauth.FabUrlServletRecepcaoOauth;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.UrlInterpretada;
import com.super_bits.modulosSB.webPaginas.controller.servletes.util.UtilFabUrlServlet;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContexts;

/**
 *
 * @author sfurbino
 * @since 10/12/2019
 * @version 1.0
 */
public class UtilSBApiRestClient {

    public static InfoConsumoRestService getInformacoesConsumoRest(ItfFabricaIntegracaoRest pConexao) {

        try {
            Field campo = pConexao.getClass().getField(pConexao.toString());
            return campo.getAnnotation(InfoConsumoRestService.class);
        } catch (Throwable t) {
            return null;
        }

    }

    public static InfoConfigRestClientIntegracao getInfoConfigRest(ItfFabricaIntegracaoRest pConexao) {
        try {
            return pConexao.getClass().getAnnotation(InfoConfigRestClientIntegracao.class);
        } catch (Throwable t) {
            return null;
        }

    }

    public static RespostaWebServiceSimples getRespostaRest(ChamadaHttpSimples pChamada) {
        return getRespostaRest(pChamada.getUrlRequisicao(), pChamada.getTipoConexao(), pChamada.isPossuiCorpoComConteudo(),
                pChamada.getCabecalhos(), pChamada.getCorpo());
    }

    private static void configurarAmbienteIgnorarQualidadeCertificadoSSL() {
        try {
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new TrustAllStrategy())
                    .build();
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        } catch (Throwable t) {

        }
    }

    public static RespostaWebServiceSimples getRespostaRest(String pURL, FabTipoConexaoRest pTipoConexao,
            boolean pPostarInformcoesCorpoRequisicao,
            Map<String, String> pCabecalho, String pCorpoRequisicao) {
        return getRespostaRest(pURL, pTipoConexao, pPostarInformcoesCorpoRequisicao, pCabecalho, pCorpoRequisicao, true);

    }

    public static HttpURLConnection getHTTPConexaoPadrao(final String url, final boolean pInorarValidacaoCertificado) throws MalformedURLException, IOException {
        URL endereco = new URL(url);

        if (url.startsWith("https")) {
            HttpsURLConnection conn = (HttpsURLConnection) endereco.openConnection();

            try {
                if (pInorarValidacaoCertificado) {
                    SSLContext sslcontext = SSLContexts.custom()
                            .loadTrustMaterial(new TrustAllStrategy())
                            .build();
                    conn.setSSLSocketFactory(sslcontext.getSocketFactory());
                    HostnameVerifier verificadorDeDominioResaponsavelPromiscuo = new HostnameVerifier() {
                        @Override
                        public boolean verify(String string, SSLSession ssls) {
                            return true;
                        }
                    };
                    conn.setHostnameVerifier(verificadorDeDominioResaponsavelPromiscuo);
                }
                return conn;
            } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException t) {
                throw new UnsupportedOperationException("Falha conectando com " + url + " " + t.getMessage());
            }
        } else {
            HttpURLConnection conn = (HttpURLConnection) endereco.openConnection();
            return conn;
        }

    }

    /**
     * TODO: Mudar o padrão para validar o certificado do SSL, porém só pode ser
     * feito, quando este parametro puder ser configurado nas anotações de de
     * integrador
     *
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static HttpURLConnection getHTTPConexaoPadrao(String url) throws MalformedURLException, IOException {
        return getHTTPConexaoPadrao(url, true);
    }

    public static RespostaWebServiceSimples getRespostaRest(String pURL, FabTipoConexaoRest pTipoConexao,
            boolean pPostarInformcoesCorpoRequisicao,
            Map<String, String> pCabecalho, String pCorpoRequisicao, boolean ignorarValidacaoCertificadoSSL) {
        String respostaStr = "";
        try {
            // Um get pode ter corpo? validar essa informção antes de ativar
            //if (pCorpoRequisicao != null && !pCorpoRequisicao.isEmpty()) {
            //  pPostarInformcoesCorpoRequisicao = true;
            //}
            System.out.println("conectando com" + pURL);

            HttpURLConnection conn = getHTTPConexaoPadrao(pURL);

            conn.setRequestMethod(pTipoConexao.getMetodoRequest());
            pCabecalho.keySet().forEach((cabecalho) -> {
                conn.setRequestProperty(cabecalho, pCabecalho.get(cabecalho));
            });

            if (pPostarInformcoesCorpoRequisicao) {

                if (pCorpoRequisicao != null) {
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), Charset.forName("UTF-8").newEncoder());
                    wr.write(pCorpoRequisicao);
                    wr.flush();
                }
            }

            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            } catch (IOException io) {
                System.out.println("Erro obtendo stream via " + pURL);
            } catch (Throwable t) {
                System.out.println("Erro obtendo stream via " + pURL);
            }

            String inputResposta;

            if (br != null) {
                while ((inputResposta = br.readLine()) != null) {
                    respostaStr += inputResposta;
                }
            }

            int codigoResposta = conn.getResponseCode();
            String mensagemErro = "";
            if (codigoResposta < 200 || codigoResposta > 220) {
                mensagemErro = conn.getResponseCode() + conn.getResponseMessage();
                try {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    ArrayList<String> respostaErro = new ArrayList<>();
                    String linha = br.readLine();
                    while (linha != null) {
                        respostaErro.add(linha);
                        linha = br.readLine();
                    }
                    mensagemErro += UtilSBCoreStringListas.getStringDaListaComBarraN(respostaErro);

                } catch (IOException t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro processando informações de erro", t);
                }

                //throw new RuntimeException("Falha Comunicação com serviço rest : HTTP error codidigo : " + conn.getResponseCode() + " Mensagem:" + mensagemErro);
            }

            conn.disconnect();
            if (UtilSBCoreStringValidador.isNuloOuEmbranco(respostaStr)) {
                if (!UtilSBCoreStringValidador.isNuloOuEmbranco(respostaStr)) {
                    respostaStr = mensagemErro;
                }
            }
            return new RespostaWebServiceSimples(codigoResposta, respostaStr, mensagemErro);
        } catch (SSLHandshakeException sslHadshakError) {
            if (!respostaStr.isEmpty()) {
                return new RespostaWebServiceSimples(0, respostaStr, respostaStr);
            } else {
                return new RespostaWebServiceSimples(0, "", "Erro de HandShake com a url" + pURL);
            }

        } catch (SocketTimeoutException socketTimeout) {
            return new RespostaWebServiceSimples(0, "", "O Servidor não respondeu no praso máximo aguardando retorno em" + pURL);
        } catch (ConnectException execesasoConexao) {
            return new RespostaWebServiceSimples(0, "", "Falha de conexão com " + pURL + " certifique que o endereço esteja acessível para a aplicação");
        } catch (IllegalArgumentException argumentoIlegal) {
            return new RespostaWebServiceSimples(0, "", "Falha conectando com " + pURL + " argumento ilegal um header não permitido?");
        } catch (IOException | RuntimeException t) {
            return null;
        } catch (Throwable t) {
            return new RespostaWebServiceSimples(0, "", "Erro não previsto acessando:" + pURL);
        }

    }

    public static ItfAcaoApiRest getAcaoDoContexto(ItfFabricaIntegracaoApi p, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        Class classeImp = UtilSBIntegracaoClientReflexao.getClasseImplementacao((ItfFabricaIntegracaoApi) p);
        try {
            if (pTipoAgente.equals(FabTipoAgenteClienteApi.USUARIO) && pUsuario == null) {
                pUsuario = SBCore.getUsuarioLogado();
            }
            return (ItfAcaoApiRest) classeImp.getConstructor(FabTipoAgenteClienteApi.class, ItfUsuario.class, Object[].class).newInstance(pTipoAgente, pUsuario, new Object[]{pParametros});
        } catch (SecurityException ex) {
            Logger.getLogger(UtilSBApiRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(UtilSBApiRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilSBApiRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilSBApiRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(UtilSBApiRestClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(UtilSBApiRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean receberCodigoSolicitacaoOauth(HttpServletRequest req) {

        try {
            UrlInterpretada parametrosDeUrl;

            parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlServletRecepcaoOauth.class, req);

            String nomeParametroRetorno = parametrosDeUrl.getValorComoString(FabUrlServletRecepcaoOauth.NOME_PARAMETRO);
            String nomeModulo = parametrosDeUrl.getValorComoString(FabUrlServletRecepcaoOauth.IDENTIFICADOR_API);
            String nomeParametro = req.getParameter(nomeParametroRetorno);
            String codigoSolicitacoa = req.getParameter(nomeParametroRetorno);

            if (nomeParametro != null) {
                TipoClienteOauth tipoCliente = (TipoClienteOauth) parametrosDeUrl.getValorComoBeanSimples(FabUrlServletRecepcaoOauth.TIPO_CLIENTE_OAUTH);
                ItfTokenGestaoOauth conexao = null;
                switch (tipoCliente.getEnumVinculado()) {
                    case USUARIO:
                        conexao = MapaTokensGerenciados.getAutenticadorUsuario(nomeModulo, SBCore.getUsuarioLogado()).getComoGestaoOauth();
                        break;
                    case SISTEMA:
                        conexao = MapaTokensGerenciados.getAutenticadorSistema(nomeModulo).getComoGestaoOauth();
                        break;
                    default:
                        throw new AssertionError(tipoCliente.getEnumVinculado().name());

                }

                if (conexao != null) {
                    conexao.setCodigoSolicitacao(codigoSolicitacoa);
                    if (UtilSBCoreStringValidador.isNAO_NuloNemBranco(codigoSolicitacoa)) {

                        conexao.gerarNovoToken();
                    } else {
                        System.out.println("A conexão Oath existe, porém, o parametro [" + nomeParametro + "] não foi encontrado com o código de soliciação");
                    }
                } else {
                    System.out.println("COnexões não encontradas, as conexoões deste modulo registradas são:");
                    MapaTokensGerenciados.printConexoesAtivas();
                }

            }
            return true;
        } catch (Throwable t) {
            return false;
        }

    }

    public static String gerarUrlServicoReceberCodigoSolicitacaoPadrao(ItfTokenGestaoOauth pEndPoint) {
        return gerarUrlServicoReceberCodigoSolicitacaoPadrao(pEndPoint, "code");
    }

    public static String gerarUrlServicoReceberCodigoSolicitacaoPadrao(ItfTokenGestaoOauth pEndPoint, String pCaminhoParametroCodigo) {

        return pEndPoint.getConfig().getPropriedadePorAnotacao(FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API_RECEPCAO_TOKEN_OAUTH)
                + "/solicitacaoAuth2Recept/" + pCaminhoParametroCodigo + "/" + UtilSBCoreStringSlugs.gerarSlugSimples(pEndPoint.getTipoAgente().getRegistro().getNome())
                + "/" + pEndPoint.getClass().getSimpleName() + "/";

    }

}
