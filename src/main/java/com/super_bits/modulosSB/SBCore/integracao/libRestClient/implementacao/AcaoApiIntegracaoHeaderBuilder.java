/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfApiRestHeaderPadrao;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author desenvolvedorninja01
 * @since 13/12/2019
 * @version 1.0
 */
public class AcaoApiIntegracaoHeaderBuilder implements ItfApiRestHeaderPadrao {

    protected final AcaoApiIntegracaoRestAbstratoBasico acao;
    protected final Map<String, String> cabecalho;

    public AcaoApiIntegracaoHeaderBuilder(ItfAcaoApiRest acao) {
        this.acao = (AcaoApiIntegracaoRestAbstratoBasico) acao;
        cabecalho = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaderPadrao() {
        if (cabecalho.isEmpty()) {
            buildHeaderPadrao();
        }
        return cabecalho;
    }

    public void buildHeaderPadrao() {

        if (acao.infoRest.tipoInformacaoEnviada().getMediaType() != null) {
            cabecalho.put(HttpHeaders.CONTENT_TYPE, acao.infoRest.tipoInformacaoEnviada().getMediaType().toString());
        }
        if (acao.infoRest.adicionarAutenticacaoBearer()) {
            cabecalho.put("Authorization", "Bearer " + acao.getTokenGestao().getToken());
        }
        if (acao.isMetodoEnviaDados()) {
            cabecalho.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(acao.getCorpoRequisicao().length()));
        }
        // Muitos serviços REST exigem a definição do usuário agente.
        cabecalho.put("User-Agent", "coletivoJavaApiClient (1.0) " + SBCore.getNomeProjeto());

    }

}
