/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import org.json.simple.JSONObject;

/**
 * @author sfurbino
 * @since 12/12/2019
 * @version 1.0
 */
public interface ItfTokenGestao {

    public String getToken();

    public boolean isTemTokemAtivo();

    public String gerarNovoToken();

    public boolean excluirToken();

    public boolean isPossuiAutenticacaoDeUsuario();

    public FabStatusToken getStatusToken();

    public FabTipoAgenteClienteRest getTipoAgente();

    public boolean validarToken();

    public boolean armazenarRespostaToken(String pJson);

    public String loadTokenArmazenado();

    public JSONObject loadTokenArmazenadoComoJsonObject();

}
