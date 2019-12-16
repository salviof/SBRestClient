/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ConsumoWSExecucao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 * @author sfurbino
 * @since 09/12/2019
 * @version 1.0
 */
public interface ItfProcessadorRespostaPacote {

    public default FabTipoArquivoImportacao getTipoInterpretacaoDados() {
        return FabTipoArquivoImportacao.JSON;
    }

    public default String getMensagemAmigavel(String pErro) {

        return pErro;

    }

    public void gerarResposta(ConsumoWSExecucao pConsumoRest);

}
