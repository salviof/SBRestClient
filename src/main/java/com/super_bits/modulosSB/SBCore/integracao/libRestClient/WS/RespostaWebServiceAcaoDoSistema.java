/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;

/**
 *
 * @author desenvolvedorninja01
 * @since 10/09/2019
 * @version 1.0
 */
public class RespostaWebServiceAcaoDoSistema extends RespostaAcaoDoSistema {

    @Expose()
    private String retornoJson;
    @Expose
    private Resultado resultadoWS = Resultado.SUCESSO;

    public RespostaWebServiceAcaoDoSistema(Class pTipoRetorno, ComoAcaoDoSistema pAcaoDoSistema) {
        super(pTipoRetorno, pAcaoDoSistema);
    }

    @Override
    public ItfRespostaAcaoDoSistema setRetorno(Object pObjetoResultante) {
        super.setRetorno(pObjetoResultante); //To change body of generated methods, choose Tools | Templates.
        retornoJson = new Gson().toJson(pObjetoResultante);
        return this;
    }

}
