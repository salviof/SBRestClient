/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import jakarta.json.JsonObject;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sfurbino
 */
public interface ItfGestaoTokenDinamico extends ItfTokenGestao {

    public default ItfTokenDeAcessoExterno extrairToken(String pString) {

        try {
            JsonObject json = UtilSBCoreJson.getJsonObjectByTexto(pString);
            if (json == null) {
                throw new UnsupportedOperationException("falha lendo json na string" + pString);
            }
            return extrairToken(json);
        } catch (Throwable ex) {

            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Impossível converter String para JsonObject, "
                    + "caso a regra de negocio não envolva o recebimento de um JsonObject, sobrescreva os Metodos extrairToken(String) e extrairToken(JsonObject)", ex);
            return null;
        }

    }

    public ItfTokenDeAcessoExterno extrairToken(JsonObject pJson);

    public boolean armazenarRespostaToken(String pJson);

    public JsonObject loadTokenArmazenadoComoJsonObject();

    public default ItfTokenDeAcessoExterno renovarToken() {
        excluirToken();
        return gerarNovoToken();
    }

}
