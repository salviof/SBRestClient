/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreWebBrowser;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sfurbino
 */
public class UtilSBApiRestClientOauth2 {

    public static final String PATH_TESTE_DE_VIDA_SERVICO_RECEPCAO = "TESTEVIDA";

    public static void solicitarAutenticacaoExterna(ItfTokenGestaoOauth pAutenticacao) {
        switch (pAutenticacao.getTipoAgente()) {

            case USUARIO:
                if (SBCore.isEmModoDesenvolvimento()) {
                    //todo abrir na pagina
                    List<String> respostaServidor = UTilSBCoreInputs.getStringsByURL(pAutenticacao.getUrlRetornoReceberCodigoSolicitacao() + "/" + PATH_TESTE_DE_VIDA_SERVICO_RECEPCAO);
                    if (respostaServidor == null) {
                        try {
                            throw new UnsupportedOperationException("O serviçoo de recepção não está ativo em " + pAutenticacao.getUrlRetornoReceberCodigoSolicitacao());
                        } catch (Exception e) {
                        }
                    }
                    String urlAutenticacao = pAutenticacao.getUrlObterCodigoSolicitacao();
                    UtilSBCoreWebBrowser.abrirLinkEmBrownser(urlAutenticacao);
                    int tentativas = 0;
                    if (!pAutenticacao.isCodigoSolicitacaoRegistrado() && tentativas < 5) {

                        try {
                            System.out.println("Aguardando Autenticação do usuário");
                            Thread.sleep(5000);
                            tentativas++;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(UtilSBApiRestClientOauth2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } else {
                    SBCore.enviarAvisoAoUsuario("Atenção, autentique redirecionando o usuário para a  a url: " + pAutenticacao.getUrlObterCodigoSolicitacao());

                }
                break;
            case SISTEMA:
                String urlAutenticacao = pAutenticacao.getUrlObterCodigoSolicitacao();
                System.out.println("Obtendo codigo de concessão via " + urlAutenticacao);
                Map<String, String> cabecalho = new HashMap<>();
                cabecalho.put("ref", "RESTFULL_OAUTH2_ADMIN_CLIENT_V1.casanovadigital.com.br");
                RespostaWebServiceSimples respostaSolicitaca = UtilSBApiRestClient
                        .getRespostaRest(urlAutenticacao, FabTipoConexaoRest.GET, false, cabecalho, null);
                if (respostaSolicitaca.isSucesso()) {

                    System.out.println(respostaSolicitaca.getRespostaTexto());
                    System.out.println("ok ");
                } else {
                    System.out.println("Falha ao obter token");
                    System.out.println(respostaSolicitaca.getRespostaTexto());
                }

                break;
            default:
                throw new AssertionError();
        }

    }

}
