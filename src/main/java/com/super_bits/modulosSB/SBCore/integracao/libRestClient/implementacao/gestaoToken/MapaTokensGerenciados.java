/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreListas;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 *
 * @author SalvioF
 */
public class MapaTokensGerenciados {

    private final static Map<String, ItfTokenGestao> AUTENTICADORES_REGISTRADOS = new HashMap<>();
    private final static Map<String, Class> API_POR_CHAVE_PUBLICA = new HashMap<>();

    public static void registrarAutenticador(ItfTokenGestao pAutenticador) {
        registrarAutenticadorUsuario(pAutenticador, null);

    }

    public static String gerarIdIdentificador(Class<? extends ItfTokenGestao> pClasseGestaoToken, ComoUsuario pUsuario) {
        if (pClasseGestaoToken == null) {
            throw new UnsupportedOperationException("Gestor de token não foi enviado");
        }
        return gerarIdIdentificador(pClasseGestaoToken.getSimpleName(), pUsuario, null);
    }

    public static String gerarIdIdentificador(Class<? extends ItfTokenGestao> pClasseGestaoToken, ComoUsuario pUsuario, String pTipoAplicacao) {
        if (pClasseGestaoToken == null) {
            throw new UnsupportedOperationException("Gestor de token não foi enviado");
        }
        return gerarIdIdentificador(pClasseGestaoToken.getSimpleName(), pUsuario, pTipoAplicacao);
    }

    public static String gerarIdIdentificador(String simpleNameGestaoToken, ComoUsuario pUsuario) {
        return gerarIdIdentificador(simpleNameGestaoToken, pUsuario, null);
    }

    public static String gerarIdIdentificador(String simpleNameGestaoToken, ComoUsuario pUsuario, String pTipoAplicacao) {
        if (simpleNameGestaoToken == null) {
            throw new UnsupportedOperationException("Gestor de token não foi enviado");
        }
        StringBuilder identificador = new StringBuilder();
        identificador.append(simpleNameGestaoToken);
        if (pTipoAplicacao != null) {
            identificador.append(".");
            identificador.append(pTipoAplicacao);
        }
        if (pUsuario != null) {
            if (pUsuario.getEmail() != null) {
                identificador.append(".");
                identificador.append(pUsuario.getEmail());
            } else {

                identificador.append(".");
                identificador.append(pUsuario.getId());
            }

        }

        return identificador.toString();
    }

    public static String gerarIdIdentificador(ItfTokenGestao pGetao, ComoUsuario pUsuario) {
        return gerarIdIdentificador(pGetao.getClass(), pUsuario);

    }

    public static void registrarAutenticadorUsuario(ItfTokenGestao pAutenticador, ComoUsuario pUsuario, String pTipoAplicacao) {
        String identificacaoGestaoToken = gerarIdIdentificador(pAutenticador.getClass(), pUsuario, pTipoAplicacao);
        AUTENTICADORES_REGISTRADOS.put(identificacaoGestaoToken, pAutenticador);
    }

    public static void registrarAutenticadorRestfullTipoApp(ItfTokenGestao pAutenticador, String pTipoAplicacao) {
        String identificacaoGestaoToken = gerarIdIdentificador(pAutenticador.getClass(), null, pTipoAplicacao);
        AUTENTICADORES_REGISTRADOS.put(identificacaoGestaoToken, pAutenticador);
    }

    public static void registrarAutenticadorUsuario(ItfTokenGestao pAutenticador, ComoUsuario pUsuario) {
        AUTENTICADORES_REGISTRADOS.put(gerarIdIdentificador(pAutenticador, pUsuario), pAutenticador);
    }

    /**
     *
     * @param gestaoToken
     * @param pUsuario
     * @return
     */
    public static ItfTokenGestao getAutenticadorUsuario(@NotNull final Class<? extends ItfTokenGestao> gestaoToken, @NotNull ComoUsuario pUsuario) {
        if (pUsuario == null) {
            throw new UnsupportedOperationException("Enviado nulo obtendo chaves de acesso do usuario para integração com " + gestaoToken.getSimpleName());
        }
        String identificador = gerarIdIdentificador(gestaoToken, pUsuario);

        return AUTENTICADORES_REGISTRADOS.get(identificador);
    }

    public static ItfTokenGestao getAutenticadorUsuario(@NotNull final Class<? extends ItfTokenGestao> gestaoToken, @NotNull ComoUsuario pUsuario, String pIdentificadorAplicacao) {
        if (pUsuario == null) {
            throw new UnsupportedOperationException("Enviado nulo obtendo chaves de acesso do usuario para integração com " + gestaoToken.getSimpleName());
        }
        String identificador = gerarIdIdentificador(gestaoToken, pUsuario, pIdentificadorAplicacao);

        return AUTENTICADORES_REGISTRADOS.get(identificador);
    }

    public static ItfTokenGestao getAutenticadorSistema(@NotNull final String pSimplenameGestaoToken) {
        return AUTENTICADORES_REGISTRADOS.get(pSimplenameGestaoToken);
    }

    private static String gerarIdIdentificador(@NotNull final Class<? extends ItfTokenGestao> pSimplenameGestaoToken) {
        return gerarIdIdentificador(pSimplenameGestaoToken, null);
    }

    public static ItfTokenGestao getAutenticadorSistema(@NotNull final Class<? extends ItfTokenGestao> pSimplenameGestaoToken) {

        return getAutenticadorSistema(pSimplenameGestaoToken.getSimpleName());
    }

    public static ItfTokenGestao getAutenticadorUsuario(@NotNull final String pSimplenameGestaoToken, @NotNull ComoUsuario pUsuario, @NotNull String pIdentificadorAplicacao) {
        if (pUsuario == null) {
            throw new UnsupportedOperationException("Enviado nulo obtendo chaves de acesso do usuario para integração com " + pSimplenameGestaoToken);
        }
        String identificador = gerarIdIdentificador(pSimplenameGestaoToken, pUsuario, pIdentificadorAplicacao);
        return AUTENTICADORES_REGISTRADOS.get(identificador);
    }

    public static ItfTokenGestao getAutenticadorSistema(ComoFabricaIntegracaoApi api, String pIdeIdentificadorAplicacao) {
        String identificador = gerarIdIdentificador(api.getClasseGestaoOauth(), null, pIdeIdentificadorAplicacao);
        return AUTENTICADORES_REGISTRADOS.get(identificador);
    }

    public static ItfTokenGestao getAutenticadorSistema(String pModuloGestorToken, String pIdeIdentificadorAplicacao) {
        String identificador = gerarIdIdentificador(pModuloGestorToken, null, pIdeIdentificadorAplicacao);
        return AUTENTICADORES_REGISTRADOS.get(identificador);
    }

    public static ItfTokenGestao getAutenticadorSistema(ComoFabricaIntegracaoApi api) {
        ItfTokenGestao token = AUTENTICADORES_REGISTRADOS.get(gerarIdIdentificador(api.getClasseGestaoOauth()));
        if (token == null) {
            api.getGestaoToken();
        }

        return getAutenticadorSistema(api.getClasseGestaoOauth());
    }

    public static ItfTokenGestao getAutenticadorUsuario(ComoFabricaIntegracaoApi api, ComoUsuario pUsuario) {

        return getAutenticadorUsuario(api.getClasseGestaoOauth(),
                pUsuario);
    }

    public static ItfTokenGestao getAutenticadorUsuarioLogado(ComoFabricaIntegracaoRest api) {
        if (AUTENTICADORES_REGISTRADOS.get(gerarIdIdentificador(api.getClasseGestaoOauth(), SBCore.getUsuarioLogado())) == null) {
            api.getGestaoToken(SBCore.getUsuarioLogado());
        }

        return AUTENTICADORES_REGISTRADOS.get(gerarIdIdentificador(api.getClasseGestaoOauth(), SBCore.getUsuarioLogado()));
    }

    public static void printConexoesAtivas() {
        System.out.println("Atenticadores Registrados:");
        System.out.println(UtilSBCoreListas.getValoresSeparadosPorPontoVirgula(Lists.newArrayList(AUTENTICADORES_REGISTRADOS.keySet())));
        System.out.println("Api por Chace pública:");
        System.out.println(UtilSBCoreListas.getValoresSeparadosPorPontoVirgula(Lists.newArrayList(API_POR_CHAVE_PUBLICA.keySet())));

    }
}
