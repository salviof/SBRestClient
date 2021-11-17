/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import java.util.Date;

/**
 *
 * @author sfurbino
 */
public class TokenDeAcessoExternoDinamico extends TokenDeAcessoExternoSimples {

    private Date dataHoraExpira;

    public TokenDeAcessoExternoDinamico(String pToken, Date pDataHoraExipira) {
        super(pToken);
        dataHoraExpira = pDataHoraExipira;
    }

    public Date getDataHoraExpira() {
        return dataHoraExpira;
    }

    @Override
    public boolean isTokenValido() {
        if (!super.isTokenValido()) {
            return false;
        }
        if (dataHoraExpira == null) {
            return true;
        }
        return new Date().getTime() < dataHoraExpira.getTime();
    }

}
