/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.ItfApiServicoTokenCliente;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClientReflexao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * Fabrica pronta para ser utilizada com o consumo de webservice forncido pelo
 * sistema de integração
 *
 *
 * @author desenvolvedor
 * @param <T>
 */
public interface ItfFabricaIntegracaoRest {

    public default InfoConsumoRestService getInformacoesConsumo() {
        return UtilSBApiRestClient.getInformacoesConsumoRest(this);
    }

    public ItfApiServicoTokenCliente getApiTokenAcesso();

    public default ItfAcaoApiRest getAcao(Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteRest.SISTEMA, null, parametros);
    }

    public default ItfAcaoApiRest getAcao(ItfUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteRest.USUARIO, pUsuario, parametros);
    }

    public default ItfAcaoApiRest getAcaoUsuarioLogado(ItfUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteRest.USUARIO, SBCore.getUsuarioLogado(), parametros);
    }

    public default ItfTokenGestao getGestaoToken() {
        ItfTokenGestao tokenGestao = MapaTokensGerenciados.getAutenticadorSistemaAtual(this);
        if (tokenGestao == null) {
            tokenGestao = UtilSBApiRestClientReflexao.getNovaInstanciaGestaoAutenticador(this, FabTipoAgenteClienteRest.SISTEMA, null);
            MapaTokensGerenciados.registrarAutenticador(tokenGestao, this);
        }
        return MapaTokensGerenciados.getAutenticadorSistemaAtual(this);

    }

    public default ItfTokenGestao getGestaoToken(ItfUsuario pUsuario) {
        ItfTokenGestao tokenGestao = MapaTokensGerenciados.getAutenticadorUsuarioLogado(this);
        if (tokenGestao == null) {
            tokenGestao = UtilSBApiRestClientReflexao.getNovaInstanciaGestaoAutenticador(this, FabTipoAgenteClienteRest.USUARIO, pUsuario);
            MapaTokensGerenciados.registrarAutenticadorUsuario(tokenGestao, this.getClass(), pUsuario);
        }
        return MapaTokensGerenciados.getAutenticadorUsuario(this, pUsuario);
    }

    public default ConfigModulo getConfiguracao() {
        return UtilSBApiRestClientReflexao.getConfigmodulo(this);
    }

    public default InfoConfigRestClientIntegracao getDadosIntegracao() {
        return UtilSBApiRestClient.getInfoConfigRest(this);
    }

}
