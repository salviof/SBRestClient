/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreListas;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import static com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest.SISTEMA;
import static com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest.USUARIO;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SalvioF
 */
public class MapaTokensGerenciados {

    private final static Map<String, ItfTokenGestao> AUTENTICADORES_REGISTRADOS = new HashMap<>();
    private final static Map<String, Class> API_POR_CHAVE_PUBLICA = new HashMap<>();

    private static String getIdentificacaoAPIUsuario(String codigoApi) {
        return getIdentificacaoAPIUsuario(codigoApi, SBCore.getUsuarioLogado());
    }

    private static String getIdentificacaoAPIUsuario(String codigoApi, ItfUsuario pUsuario) {
        return codigoApi + pUsuario.getEmail();
    }

    private static String getIdentificacaoAPISistema(String codigoApi) {
        return codigoApi + SBCore.getGrupoProjeto();
    }

    public static void registrarAutenticador(ItfTokenGestao pAutenticador, String codigoApi) {
        switch (pAutenticador.getTipoAgente()) {
            case USUARIO:
                AUTENTICADORES_REGISTRADOS.put(getIdentificacaoAPIUsuario(codigoApi), pAutenticador);
                break;
            case SISTEMA:
                AUTENTICADORES_REGISTRADOS.put(getIdentificacaoAPISistema(codigoApi), pAutenticador);
                break;
            default:
                throw new AssertionError(pAutenticador.getTipoAgente().name());
        }

    }

    public static void registrarAutenticador(ItfTokenGestao pAutenticador, ItfFabricaIntegracaoRest api) {
        registrarAutenticador(pAutenticador, api.getClass().getSimpleName());

    }

    public static ItfTokenGestao getAutenticadorUsuarioLogado(ItfFabricaIntegracaoRest api) {
        return AUTENTICADORES_REGISTRADOS.get(getIdentificacaoAPIUsuario(api.getClass().getSimpleName()));
    }

    public static ItfTokenGestao getAutenticadorUsuarioLogado(ItfFabricaIntegracaoRest api, ItfUsuario pUsuario) {
        return AUTENTICADORES_REGISTRADOS.get(getIdentificacaoAPIUsuario(api.getClass().getSimpleName()));
    }

    public static ItfTokenGestao getAutenticadorSistemaAtual(ItfFabricaIntegracaoRest api) {
        return AUTENTICADORES_REGISTRADOS.get(getIdentificacaoAPISistema(api.getClass().getSimpleName()));
    }

    public static ItfTokenGestao getAutenticadorSistemaAtual(String api) {
        return AUTENTICADORES_REGISTRADOS.get(getIdentificacaoAPISistema(api));
    }

    public static ItfTokenGestao getAutenticadorUsuarioLogado(String api) {
        return AUTENTICADORES_REGISTRADOS.get(getIdentificacaoAPIUsuario(api));
    }

    public static void printConexoesAtivas() {
        System.out.println("Atenticadores Registrados:");
        System.out.println(UtilSBCoreListas.getValoresSeparadosPorPontoVirgula(Lists.newArrayList(AUTENTICADORES_REGISTRADOS.keySet())));
        System.out.println("Api por Chace pública:");
        System.out.println(UtilSBCoreListas.getValoresSeparadosPorPontoVirgula(Lists.newArrayList(API_POR_CHAVE_PUBLICA.keySet())));

    }
}
