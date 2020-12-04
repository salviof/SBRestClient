/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;

/**
 *
 * @author sfurbino
 */
public class UtilSBApiRestClientOauth2 {

    public static final String PATH_TESTE_DE_VIDA_SERVICO_RECEPCAO = "TESTEVIDA";

    public static void solicitarAutenticacaoExterna(ItfTokenGestaoOauth pAutenticacao) {

        if (SBCore.isEmModoProducao()) {

        } else if (SBCore.isEmModoDesenvolvimento()) {

        } else {

        }

    }

}
