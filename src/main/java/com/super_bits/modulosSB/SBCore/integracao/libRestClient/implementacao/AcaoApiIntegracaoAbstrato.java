/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author SalvioF
 */
public abstract class AcaoApiIntegracaoAbstrato extends AcaoApiIntegracaoRestAbstratoBasico {

    public AcaoApiIntegracaoAbstrato(ItfFabricaIntegracaoRest pIntegracaoEndpoint,
            FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        super(pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);

    }

}
