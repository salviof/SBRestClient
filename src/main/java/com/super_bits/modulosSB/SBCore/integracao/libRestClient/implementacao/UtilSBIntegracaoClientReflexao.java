/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringPosicaoLocalizar;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringsCammelCase;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfValidacao;
import org.reflections.ReflectionUtils;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfApiRestHeaderPadrao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 * @since 11/12/2019
 * @version 1.0
 */
public class UtilSBIntegracaoClientReflexao {

    public static InfoConfigRestClientIntegracao getInfoConfigWebService(ComoFabricaIntegracaoApi p) {
        return getInfoConfigWebService(p.getClass());
    }

    public static InfoConfigRestClientIntegracao getInfoConfigWebService(Class<? extends ComoFabricaIntegracaoApi> p) {
        return p.getAnnotation(InfoConfigRestClientIntegracao.class);
    }

    public static String getNomeClasseAnotacao(ComoFabricaIntegracaoApi p) {
        InfoConfigRestClientIntegracao info = getInfoConfigWebService(p);
        String nomeClasse = p.getClass().getSimpleName();
        String descricaoModulo = nomeClasse.substring(UtilCRCStringPosicaoLocalizar.getUltimaLetraMaiuscula(nomeClasse), nomeClasse.length());
        return "InfoIntegracaoRest" + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiusculaSemCaracterEspecial(info.nomeIntegracao()) + descricaoModulo;
    }

    public static String getNomeClasseImplementacao(ComoFabricaIntegracaoApi p) {
        InfoConfigRestClientIntegracao info = getInfoConfigWebService(p);
        if (info == null) {
            throw new UnsupportedOperationException(InfoConfigRestClientIntegracao.class.getSimpleName() + "Não foi definido para" + p.getClass().getSimpleName());
        }
        String nome = info.nomeIntegracao();

        return "IntegracaoRest" + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiuscula(nome) + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiuscula(p.toString());
    }

    public static String getNomeClasseImplementacaoGestaoToken(Class<? extends ComoFabricaIntegracaoApi> p) {
        InfoConfigRestClientIntegracao info = getInfoConfigWebService(p);
        if (info == null) {
            throw new UnsupportedOperationException(InfoConfigRestClientIntegracao.class.getSimpleName() + "Não foi definido para" + p.getClass().getSimpleName());
        }
        String nome = info.nomeIntegracao();

        return "GestaoTokenRest" + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiuscula(nome);
    }

    public static String getNomeClasseImplementacaoGestaoToken(ComoFabricaIntegracaoApi p) {
        return getNomeClasseImplementacaoGestaoToken(p.getClass());
    }

    public static String getNomeClasseImplementacaoGestaoHeaderPadrao(ComoFabricaIntegracaoApi p) {
        InfoConfigRestClientIntegracao info = getInfoConfigWebService(p);
        if (info == null) {
            throw new UnsupportedOperationException(InfoConfigRestClientIntegracao.class.getSimpleName() + "Não foi definido para" + p.getClass().getSimpleName());
        }
        String nome = info.nomeIntegracao();

        return "IntegracaoRest" + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiuscula(nome) + "_HeaderPadrao";
    }

    public static ItfApiRestHeaderPadrao getHeaderPadrao(ComoFabricaIntegracaoApi fabrica, ItfAcaoApiRest p) {
        String caminhoCompleto = getPacoteImplementacao(fabrica)
                + "." + getNomeClasseImplementacaoGestaoHeaderPadrao(fabrica);
        Class classeHeaderPadrao = (Class<? extends ItfValidacao>) ReflectionUtils.forName(caminhoCompleto);
        try {
            return (ItfApiRestHeaderPadrao) classeHeaderPadrao.getConstructor(ItfAcaoApiRest.class).newInstance(p);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(UtilSBIntegracaoClientReflexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getPacoteApi(ComoFabricaIntegracaoApi p) {
        InfoConfigRestClientIntegracao info = getInfoConfigWebService(p);
        if (info == null) {
            throw new UnsupportedOperationException(InfoConfigRestClientIntegracao.class.getSimpleName()
                    + "Não foi definido para" + p.getClass().getSimpleName());
        }
        String nome = info.nomeIntegracao();
        return "br.org.coletivoJava.integracoes.rest" + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiuscula(nome) + ".api";
    }

    public static String getPacoteImplementacao(ComoFabricaIntegracaoApi p) {
        InfoConfigRestClientIntegracao info = getInfoConfigWebService(p);
        if (info == null) {
            throw new UnsupportedOperationException(InfoConfigRestClientIntegracao.class.getSimpleName() + "Não foi definido para" + p.getClass().getSimpleName());
        }
        String nome = info.nomeIntegracao();
        return "br.org.coletivoJava.integracoes.rest" + UtilCRCStringsCammelCase.getCamelByTextoPrimeiraLetraMaiuscula(nome) + ".implementacao";
    }

    public static Class getClasseAnotacao(ComoFabricaIntegracaoApi pApi) {
        String caminhoCompleto = getPacoteApi(pApi)
                + "." + getNomeClasseAnotacao(pApi);
        Class classeAnotacao = (Class<? extends ItfValidacao>) ReflectionUtils.forName(caminhoCompleto);
        return classeAnotacao;
    }

    public static String getNomeCanonicoClasseImplementacao(ComoFabricaIntegracaoApi pApi) {
        return getPacoteImplementacao(pApi)
                + "." + getNomeClasseImplementacao(pApi);
    }

    public static Class getClasseImplementacao(ComoFabricaIntegracaoApi pApi) {
        String caminhoCompleto = getNomeCanonicoClasseImplementacao(pApi);
        Class classeValidacao = (Class<? extends ItfValidacao>) ReflectionUtils.forName(caminhoCompleto);
        return classeValidacao;
    }

    public static Class getClasseToken(ComoFabricaIntegracaoApi pApi) {
        String caminhoCompleto = getPacoteImplementacao(pApi)
                + "." + getNomeClasseImplementacaoGestaoToken(pApi);
        Class classeAnotacao = (Class<? extends ItfValidacao>) ReflectionUtils.forName(caminhoCompleto);
        return classeAnotacao;
    }

    public static ItfTokenGestao getNovaInstanciaGestaoAutenticador(ComoFabricaIntegracaoApi pApi,
            FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, String pApiIdentificador) {
        ItfTokenGestao tokenGestao = null;
        if (pTipoAgente == FabTipoAgenteClienteApi.SISTEMA) {
            pUsuario = null;
            tokenGestao = MapaTokensGerenciados.getAutenticadorSistema(pApi, pApiIdentificador);
        } else {
            tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(pApi.getClasseGestaoOauth(),
                    pUsuario, pApiIdentificador);
        }
        if (tokenGestao != null) {
            return tokenGestao;
        }
        Class classe = getClasseToken(pApi);
        boolean possuiEspecificacaoDeApi = true;
        Constructor constructorGestaoToken = null;
        try {
            constructorGestaoToken = classe.getConstructor(FabTipoAgenteClienteApi.class,
                    ComoUsuario.class, String.class);
        } catch (NoSuchMethodException ex) {
            possuiEspecificacaoDeApi = false;
            try {
                constructorGestaoToken = classe.getConstructor(FabTipoAgenteClienteApi.class, ComoUsuario.class);
            } catch (NoSuchMethodException ex1) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo gestor de tolen do cliente rest para:" + pApi + " " + classe.getSimpleName() + " não possui um código adequado", ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(UtilSBIntegracaoClientReflexao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SecurityException ex) {

        }
        ItfTokenGestao novaGestaoToken;
        try {
            if (possuiEspecificacaoDeApi) {
                novaGestaoToken = (ItfTokenGestao) constructorGestaoToken.newInstance(pTipoAgente, pUsuario, pApiIdentificador);
                MapaTokensGerenciados.registrarAutenticadorRestfullTipoApp(novaGestaoToken, pApiIdentificador);
            } else {
                novaGestaoToken = (ItfTokenGestao) constructorGestaoToken.newInstance(pTipoAgente, pUsuario);
                MapaTokensGerenciados.registrarAutenticadorUsuario(novaGestaoToken, pUsuario, pApiIdentificador);
            }

            return novaGestaoToken;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo gestor de tolen do cliente rest para:" + pApi + t.getMessage(), t);
        }
        return null;

    }

    public static ConfigModulo getConfigmodulo(ComoFabricaIntegracaoApi pApi) {
        return getConfigmodulo(pApi.getClass());
    }

    public static ConfigModulo getConfigmodulo(Class<? extends ComoFabricaIntegracaoApi> pApi) {
        InfoConfigRestClientIntegracao informacoes = pApi.getAnnotation(InfoConfigRestClientIntegracao.class);
        return SBCore.getConfigModulo(informacoes.configuracao());
    }
}
