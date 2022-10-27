/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiCliente;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author desenvolvedor
 */
public abstract class AcaoApiIntegracaoAbstratoBasico implements ItfAcaoApiCliente {

    private final ItfFabricaIntegracaoApi fabricaIntegracao;

    private ItfTokenGestao tokenGestao;

    protected ItfResposta resposta;
    private final ItfUsuario usuario;
    protected Object[] parametros;
    private final FabTipoAgenteClienteApi tipoAgente;
    private final String idTipoAplicacao;

    public AcaoApiIntegracaoAbstratoBasico(String pTipoAplicacao, ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        parametros = pParametros;
        usuario = pUsuario;
        fabricaIntegracao = pIntegracaoEndpoint;
        idTipoAplicacao = pTipoAplicacao;
        if (pParametros != null) {
            parametros = (Object[]) pParametros[0];
        } else {
            parametros = null;
        }

        if (usuario == null) {
            tipoAgente = FabTipoAgenteClienteApi.SISTEMA;
        } else {
            tipoAgente = pTipoAgente;
        }
    }

    public AcaoApiIntegracaoAbstratoBasico(ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {

        this(null, pIntegracaoEndpoint, pTipoAgente, pUsuario, pParametros);
    }

    protected abstract void executarAcao();

    @Override
    public ItfUsuario getUsuario() {
        return usuario;
    }

    @Override
    public FabTipoAgenteClienteApi getAgenteApi() {
        return tipoAgente;
    }

    @Override
    public Object[] getParametros() {
        return parametros;
    }

    @Override
    public ItfFabricaIntegracaoApi getFabricaApi() {
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
    public ItfTokenGestao getTokenGestao() {
        String prIDsistema = getIdentificadorSisteServicoByParametros(parametros);

        if (prIDsistema != null) {
            tokenGestao = MapaTokensGerenciados.getAutenticadorUsuario(fabricaIntegracao.getClasseGestaoOauth(), SBCore.getUsuarioLogado(), prIDsistema);
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
                        tokenGestao = UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(fabricaIntegracao, tipoAgente, usuario, idTipoAplicacao);
                        MapaTokensGerenciados.registrarAutenticador(tokenGestao);
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
