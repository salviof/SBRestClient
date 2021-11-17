/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfGestaoTokenDinamico;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import org.json.simple.JSONObject;

/**
 *
 * @author sfurbino
 */
public abstract class GestaoTokenDinamico extends GestaoTokenGenerico implements ItfGestaoTokenDinamico {

    public GestaoTokenDinamico(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpoints, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario) {
        super(pClasseEndpoints, pTipoAgente, pUsuario);
    }

    @Override
    public JSONObject loadTokenArmazenadoComoJsonObject() {
        switch (tipoAgente) {
            case USUARIO:
                if (usuario != null) {

                    JSONObject tokenLoad = getConfig().getRepositorioDeArquivosExternos().getJsonObjeto(usuario.getEmail());
                    //    token = extrairToken(tokenLoad);
                    return tokenLoad;
                }
                return null;
            case SISTEMA:

                JSONObject tokenLoad = getConfig().getRepositorioDeArquivosExternos().getJsonObjeto("tokensistema");
                //  token = extrairToken(tokenLoad);
                return tokenLoad;
            default:

        }
        return null;
    }

    @Override
    public ItfTokenDeAcessoExterno loadTokenArmazenado() {
        String textoArmazenado = null;
        switch (tipoAgente) {
            case USUARIO:
                if (usuario != null) {

                    textoArmazenado = getConfig().getRepositorioDeArquivosExternos().getTexto(usuario.getEmail());

                }
                break;
            case SISTEMA:
                textoArmazenado = getConfig().getRepositorioDeArquivosExternos().getTexto("tokensistema");

            default:

        }
        if (UtilSBCoreStringValidador.isNuloOuEmbranco(textoArmazenado)) {
            return null;
        }
        setToken(extrairToken(textoArmazenado));
        return getTokenCompleto();

    }

    @Override
    public boolean isTemTokemAtivo() {
        if (getTokenCompleto() == null) {
            loadTokenArmazenado();
        }
        return super.isTemTokemAtivo(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean armazenarRespostaToken(String pToken) {

        if (extrairToken(pToken) == null) {
            return false;
        }

        switch (tipoAgente) {
            case USUARIO:
                if (usuario != null) {
                    if (getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno(usuario.getEmail(), pToken)) {
                        return true;
                    }
                }

            case SISTEMA:
                if (getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno("tokensistema", pToken)) {
                    return true;
                }

            default:
                return false;

        }

    }

}
