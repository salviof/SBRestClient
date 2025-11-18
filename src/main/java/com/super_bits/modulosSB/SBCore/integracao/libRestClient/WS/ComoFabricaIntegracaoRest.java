/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

/**
 *
 * Fabrica pronta para ser utilizada com o consumo de webservice forncido pelo
 * sistema de integração
 *
 *
 * @author desenvolvedor
 * @param <T>
 */
public interface ComoFabricaIntegracaoRest extends ComoFabricaIntegracaoApi {

    public default InfoConsumoRestService getInformacoesConsumo() {
        return UtilSBApiRestClient.getInformacoesConsumoRest(this);
    }

    @Override
    public default ItfAcaoApiRest getAcao(Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteApi.SISTEMA, null, parametros);
    }

    @Override
    public default ItfAcaoApiRest getAcao(FabTipoAgenteClienteApi pTipo, ComoUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, pTipo, pUsuario, parametros);
    }

    @Override
    public default ItfAcaoApiRest getAcao(ComoUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteApi.USUARIO, pUsuario, parametros);
    }

    @Override
    public default ItfAcaoApiRest getAcaoUsuarioLogado(ComoUsuario pUsuario, Object... parametros) {
        return UtilSBApiRestClient.getAcaoDoContexto(this, FabTipoAgenteClienteApi.USUARIO, SBCore.getUsuarioLogado(), parametros);
    }

    public default InfoConfigRestClientIntegracao getDadosIntegracao() {
        return UtilSBApiRestClient.getInfoConfigRest(this);
    }
}
