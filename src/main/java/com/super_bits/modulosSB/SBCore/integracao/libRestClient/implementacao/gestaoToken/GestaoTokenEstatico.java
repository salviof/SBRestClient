/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author sfurbino
 */
public abstract class GestaoTokenEstatico extends GestaoTokenGenerico {

    public GestaoTokenEstatico(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpoints, FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario) {
        super(pClasseEndpoints, pTipoAgente, pUsuario);
    }

}
