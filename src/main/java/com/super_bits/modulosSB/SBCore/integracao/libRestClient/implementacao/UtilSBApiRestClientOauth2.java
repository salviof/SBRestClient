/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreWebBrowser;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sfurbino
 */
public class UtilSBApiRestClientOauth2 {

    public static final String PATH_TESTE_DE_VIDA_SERVICO_RECEPCAO = "TESTEVIDA";

    public static void solicitarAutenticacaoExterna(ItfTokenGestaoOauth pAutenticacao) {

        if (SBCore.isEmModoProducao()) {
            //todo abrir na pagina

            SBCore.enviarAvisoAoUsuario("Atenção, autentique autentique utilizando a url: " + pAutenticacao.getUrlObterCodigoSolicitacao());

        } else if (SBCore.isEmModoDesenvolvimento()) {
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

        }

    }

}
