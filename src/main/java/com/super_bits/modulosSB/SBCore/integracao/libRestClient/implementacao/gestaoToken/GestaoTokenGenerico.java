/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.FabStatusToken;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClientReflexao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import org.json.simple.JSONObject;

/**
 *
 * @author desenvolvedorninja01
 * @since 13/12/2019
 * @version 1.0
 */
public abstract class GestaoTokenGenerico implements ItfTokenGestao {

    protected final FabTipoAgenteClienteRest tipoAgente;
    protected final ItfUsuario usuario;
    protected final Class<? extends ItfFabricaIntegracaoRest> classeFabricaAcessos;
    protected final ItfConfigModulo configuracoesAmbiente;
    private String token;

    public GestaoTokenGenerico(Class<? extends ItfFabricaIntegracaoRest> pClasseEndpoints,
            FabTipoAgenteClienteRest pTipoAgente, ItfUsuario pUsuario) {
        tipoAgente = pTipoAgente;
        usuario = pUsuario;
        configuracoesAmbiente = UtilSBApiRestClientReflexao.getConfigmodulo(pClasseEndpoints);
        classeFabricaAcessos = pClasseEndpoints;
        inicioGestaoToken();

    }

    protected void inicioGestaoToken() {

        try {
            String tokenArmazenado = loadTokenArmazenado();
            token = extrairToken(tokenArmazenado);
        } catch (Throwable t) {

        }
    }

    @Override
    public boolean isTemTokemAtivo() {
        return token != null;
    }

    @Override
    public FabTipoAgenteClienteRest getTipoAgente() {
        return tipoAgente;
    }

    @Override
    public final ConfigModulo getConfig() {
        return UtilSBApiRestClientReflexao.getConfigmodulo(classeFabricaAcessos);
    }

    @Override
    public String getToken() {
        if (token == null) {
            try {
                token = gerarNovoToken();
            } catch (Throwable t) {
                token = null;
            }
        }
        return token;
    }

    @Override
    public boolean isPossuiAutenticacaoDeUsuario() {
        return false;
    }

    @Override
    public FabStatusToken getStatusToken() {

        if (token != null) {
            return FabStatusToken.ATIVO;
        }
        return FabStatusToken.SEM_TOKEN;
    }

    @Override
    public boolean excluirToken() {
        token = null;
        switch (tipoAgente) {
            case USUARIO:
                getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno(usuario.getEmail(), "");
                break;
            case SISTEMA:
                getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno("tokensistema", "");
                break;
            default:
                throw new AssertionError(tipoAgente.name());

        }
        return true;
    }

    @Override
    public boolean armazenarRespostaToken(JSONObject pJson) {
        return armazenarRespostaToken(pJson.toString());

    }

    @Override
    public boolean armazenarRespostaToken(String pJson) {

        if (extrairToken(pJson) == null) {
            return false;
        }
        token = extrairToken(pJson);
        switch (tipoAgente) {
            case USUARIO:
                if (usuario != null) {
                    if (getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno(usuario.getEmail(), pJson)) {
                        return true;
                    }
                }

            case SISTEMA:
                if (getConfig().getRepositorioDeArquivosExternos().putConteudoRecursoExterno("tokensistema", pJson)) {
                    return true;
                }

            default:
                return false;

        }

    }

    @Override
    public JSONObject loadTokenArmazenadoComoJsonObject() {
        switch (tipoAgente) {
            case USUARIO:
                if (usuario != null) {

                    JSONObject tokenLoad = getConfig().getRepositorioDeArquivosExternos().getJsonObjeto(usuario.getEmail());
                    token = extrairToken(tokenLoad);
                    return tokenLoad;
                }
                return null;
            case SISTEMA:

                JSONObject tokenLoad = getConfig().getRepositorioDeArquivosExternos().getJsonObjeto("tokensistema");
                token = extrairToken(tokenLoad);
                return tokenLoad;
            default:

        }
        return null;
    }

    @Override
    public String loadTokenArmazenado() {
        switch (tipoAgente) {
            case USUARIO:
                if (usuario != null) {

                    return getConfig().getRepositorioDeArquivosExternos().getTexto(usuario.getEmail());

                }

            case SISTEMA:
                return getConfig().getRepositorioDeArquivosExternos().getTexto("tokensistema");

            default:

        }
        return null;
    }

}
