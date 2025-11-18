/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiCliente;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

/**
 *
 * @author sfurbino
 */
public abstract class AcaoApiIntegracaoSDKEmbarcado extends AcaoApiIntegracaoAbstratoBasico implements ItfAcaoApiCliente {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AcaoApiIntegracaoSDKEmbarcado(ComoFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, Object... pParametros) {
        super(pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
        executarAcao();
    }

}
