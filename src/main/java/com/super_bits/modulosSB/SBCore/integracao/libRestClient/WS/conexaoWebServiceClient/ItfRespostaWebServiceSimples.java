/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import org.json.simple.JSONObject;

/**
 *
 * @author desenvolvedor
 */
public interface ItfRespostaWebServiceSimples extends ItfResposta {

    public JSONObject getRespostaComoObjetoJson();

    public String getRespostaTexto();

    public int getCodigoResposta();

}
