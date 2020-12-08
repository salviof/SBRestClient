/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;

/**
 * @author sfurbino
 * @since 09/12/2019
 * @version 1.0
 */
public interface ItfAcaoApiRest extends ItfProcessadorEnvioPacote, ItfProcessadorRespostaPacote, ItfAcaoApiCliente {

    @Override
    public RespostaWebServiceSimples getResposta();

    @Override
    public ItfFabricaIntegracaoRest getFabricaApi();

}
