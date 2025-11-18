/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

/**
 *
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public abstract class AcaoApiIntegracaoComOauthAbstrato extends AcaoApiIntegracaoAbstrato {

    public AcaoApiIntegracaoComOauthAbstrato(ComoFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, Object... pParametros) {
        super(pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
    }

    public AcaoApiIntegracaoComOauthAbstrato(String pTipoApicacao, ComoFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, Object... pParametros) {
        super(pTipoApicacao, pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
    }

}
