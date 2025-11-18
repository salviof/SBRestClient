/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

/**
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public interface ItfTokenGestaoBasico extends ItfTokenGestao {

    @Override
    public default boolean isPossuiAutenticacaoDeUsuario() {
        return false;
    }

}
