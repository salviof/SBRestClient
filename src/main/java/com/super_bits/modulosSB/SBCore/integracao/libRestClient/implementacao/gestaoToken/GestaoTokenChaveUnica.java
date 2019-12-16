/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author sfurbino
 * @since 13/12/2019
 * @version 1.0
 */
public abstract class GestaoTokenChaveUnica extends GestaoTokenGenerico {

    public GestaoTokenChaveUnica(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpoints,
            FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario) {
        super(pClasseEndpoints, pTipoAgente, pUsuario);
    }

}
