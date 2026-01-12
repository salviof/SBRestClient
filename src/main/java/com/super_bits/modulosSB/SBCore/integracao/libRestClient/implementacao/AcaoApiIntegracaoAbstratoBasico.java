/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiCliente;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author desenvolvedor
 */
public abstract class AcaoApiIntegracaoAbstratoBasico implements ItfAcaoApiCliente {

    private final ComoFabricaIntegracaoApi fabricaIntegracao;

    private ItfTokenGestao tokenGestao;

    protected ItfResposta resposta;
    private final ComoUsuario usuario;
    protected List<Object> parametros;
    private final FabTipoAgenteClienteApi tipoAgente;
    private final String idTipoAplicacao;

    public AcaoApiIntegracaoAbstratoBasico(String pTipoAplicacao, ComoFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, Object... pParametros) {

        parametros = new ArrayList<>();

        if (pParametros != null) {
            if (pParametros.length == 1) {
                for (Object pr : pParametros) {
                    if (pr instanceof Object[]) {
                        Object[] prtos = (Object[]) pr;
                        for (Object prEmbended : prtos) {
                            parametros.add(prEmbended);
                        }

                    } else {
                        parametros.add(pr);
                    }
                }
            } else {
                for (Object pr : pParametros) {
                    if (pr instanceof Object[]) {
                        parametros.add(pr);
                    }

                }
            }

        }

        usuario = pUsuario;
        fabricaIntegracao = pIntegracaoEndpoint;
        idTipoAplicacao = pTipoAplicacao;

        if (usuario == null) {
            tipoAgente = FabTipoAgenteClienteApi.SISTEMA;
        } else {
            tipoAgente = pTipoAgente;
        }
    }

    public AcaoApiIntegracaoAbstratoBasico(ComoFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, Object... pParametros) {

        this(null, pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
    }

    protected abstract void executarAcao();

    @Override
    public ComoUsuario getUsuario() {
        return usuario;
    }

    @Override
    public FabTipoAgenteClienteApi getAgenteApi() {
        return tipoAgente;
    }

    @Override
    public Object[] getParametros() {

        return parametros.toArray();
    }

    @Override
    public ComoFabricaIntegracaoApi getFabricaApi() {
        return fabricaIntegracao;
    }

    private String getIdentificadorSisteServicoByParametros(Object[] pParametros) {
        if (idTipoAplicacao != null) {
            return idTipoAplicacao;
        } else {
            for (Object pr : pParametros) {
                if (pr instanceof SolicitacaoControllerERP) {

                    return ((SolicitacaoControllerERP) pr).getErpServico();
                }
                if (pr instanceof ItfSistemaERP) {
                    return ((ItfSistemaERP) pr).getHashChavePublica();
                }

            }
        }
        return null;
    }

    @Override
    public synchronized ItfTokenGestao getTokenGestao() {
        String prIDsistema = getIdentificadorSisteServicoByParametros(getParametros());

        if (prIDsistema != null) {

            tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(fabricaIntegracao.getClasseGestaoOauth(), usuario, prIDsistema);
            if (tokenGestao == null) {
                tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(fabricaIntegracao, tipoAgente, usuario, idTipoAplicacao);
                MapaTokensGerenciados.registrarAutenticadorUsuario(tokenGestao, usuario, idTipoAplicacao);
            }
            return tokenGestao;
        }
        if (tokenGestao == null) {
            switch (tipoAgente) {
                case USUARIO:
                    tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(fabricaIntegracao.getClasseGestaoOauth(), usuario, idTipoAplicacao);
                    if (tokenGestao == null) {
                        tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(fabricaIntegracao, tipoAgente, usuario, idTipoAplicacao);
                        MapaTokensGerenciados.registrarAutenticadorUsuario(tokenGestao, usuario, idTipoAplicacao);
                    }
                    break;
                case SISTEMA:
                    tokenGestao = MapaTokensGerenciados.getAutenticadorSistema(fabricaIntegracao);
                    if (tokenGestao == null) {
                        tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(fabricaIntegracao, tipoAgente, null, idTipoAplicacao);
                        MapaTokensGerenciados.registrarAutenticadorRestfullTipoApp(tokenGestao, idTipoAplicacao);
                    }
                    break;
                default:
                    throw new AssertionError(tipoAgente.name());

            }
        }

        return tokenGestao;
    }

    public String getIdTipoAplicacao() {
        return idTipoAplicacao;
    }

}
