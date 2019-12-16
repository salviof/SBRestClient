/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.util.Map;

/**
 * @author sfurbino
 * @since 09/12/2019
 * @version 1.0
 */
public interface ItfProcessadorEnvioPacote {

    public String getUrlServidor();

    public boolean isMetodoEnviaDados();

    public String gerarUrlRequisicao();

    public FabTipoConexaoRest gerarTipoRequisicao();

    public Map<String, String> gerarCabecalho();

    public String gerarCorpoRequisicao();

    public ItfTokenGestao getTokenGestao();

}
