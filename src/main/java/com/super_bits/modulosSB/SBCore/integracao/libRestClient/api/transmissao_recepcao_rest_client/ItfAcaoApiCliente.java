/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author sfurbino
 */
public interface ItfAcaoApiCliente {

    public ItfFabricaIntegracaoApi getFabricaApi();

    public FabTipoAgenteClienteApi getAgenteApi();

    public ItfUsuario getUsuario();

    public Object[] getParametros();

    public ItfResposta getResposta();

    public ItfTokenGestao getTokenGestao();

}
