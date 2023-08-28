/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.TipoClienteOauth;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoDaFabrica;

/**
 *
 * @author SalvioF
 */
public enum FabTipoAgenteClienteApi implements ItfFabrica {

    @InfoObjetoDaFabrica(id = 1, nomeObjeto = "Usuário", classeObjeto = TipoClienteOauth.class)
    USUARIO,
    @InfoObjetoDaFabrica(id = 2, nomeObjeto = "Sistema", classeObjeto = TipoClienteOauth.class)
    SISTEMA;

    @Override
    public TipoClienteOauth getRegistro() {
        return (TipoClienteOauth) ItfFabrica.super.getRegistro(); //chamada super do metodo (implementação classe pai)
    }

}
