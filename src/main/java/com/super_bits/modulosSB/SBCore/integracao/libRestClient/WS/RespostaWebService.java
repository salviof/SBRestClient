/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaSimples;

/**
 *
 * @author desenvolvedor
 */
public class RespostaWebService extends RespostaSimples {

    @Expose()
    private String retornoJson;
    @Expose
    private Resultado resultadoWS = Resultado.SUCESSO;

    public RespostaWebService() {
        super(null);
    }

    @Override
    public ItfResposta setRetorno(Object pObjetoResultante) {
        super.setRetorno(pObjetoResultante); //To change body of generated methods, choose Tools | Templates.
        retornoJson = new Gson().toJson(pObjetoResultante);
        return this;
    }

    public String getRetornoJson() {
        return retornoJson;
    }

    public void setRetornoJson(String retornoJson) {
        this.retornoJson = retornoJson;
    }

    @Override
    public void setResultadoWS(Resultado resultadoWS) {
        super.setResultadoWS(resultadoWS);
        this.resultadoWS = resultadoWS;

    }

}
