/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public interface ItfTokenGestao {

    public String getToken();

    public ConfigModulo getConfig();

    public boolean isTemTokemAtivo();

    public String gerarNovoToken();

    public boolean excluirToken();

    public boolean isPossuiAutenticacaoDeUsuario();

    public FabStatusToken getStatusToken();

    public FabTipoAgenteClienteRest getTipoAgente();

    public boolean validarToken();

    public boolean armazenarRespostaToken(String pJson);

    public boolean armazenarRespostaToken(JSONObject pJson);

    public String loadTokenArmazenado();

    public JSONObject loadTokenArmazenadoComoJsonObject();

    public default ItfTokenGestaoOauth getComoGestaoOauth() {
        return (ItfTokenGestaoOauth) this;
    }

    public default String extrairToken(String pString) {
        JSONParser parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(pString);
            return extrairToken(json);
        } catch (ParseException ex) {

            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Impossível converter String para JsonObject, caso a regra de negocio não envolva o recebimento de um JsonObject, sobrescreva os Metodos extrairToken(String) e extrairToken(JsonObject)", ex);
            return null;
        }

    }

    public String extrairToken(JSONObject pJson);

}
