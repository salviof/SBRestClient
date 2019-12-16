/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public abstract class AcaoApiIntegracaoComOauthAbstrato extends AcaoApiIntegracaoAbstrato {

    public AcaoApiIntegracaoComOauthAbstrato(ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        super(pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
    }

}
