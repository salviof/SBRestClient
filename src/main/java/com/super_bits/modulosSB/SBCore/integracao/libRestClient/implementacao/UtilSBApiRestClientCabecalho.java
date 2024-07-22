/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author salvio
 */
public class UtilSBApiRestClientCabecalho {

    public static void adicionarAgentePadrao(Map<String, String> pCabecalho) {
        pCabecalho.put(HttpHeaders.USER_AGENT, "coletivoJavaApiClient (1.0) " + SBCore.getNomeProjeto());
    }

    public static void adiacionarTipoREsposta(Map<String, String> pCabecalho, MediaType pTipo) {
        pCabecalho.put(HttpHeaders.CONTENT_TYPE, pTipo.getSubtype());
    }

    public static void adicionarBearer(Map<String, String> pCabecalho, String pChave) {
        pCabecalho.put(HttpHeaders.AUTHORIZATION, "Bearer " + pChave);
    }

    public static Map<String, String> criarCabeCalhoPadraoBearer(String pCodigo) {
        Map<String, String> cabecalho = new HashMap<>();
        adicionarAgentePadrao(cabecalho);
        //  adiacionarTipoREsposta(cabecalho, MediaType.APPLICATION_JSON_TYPE);
        adicionarBearer(cabecalho, pCodigo);
        return cabecalho;
    }
}
