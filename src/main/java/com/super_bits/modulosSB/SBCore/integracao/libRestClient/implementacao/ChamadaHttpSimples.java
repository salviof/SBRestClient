/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import java.util.Map;

/**
 *
 * @author sfurbino
 */
public class ChamadaHttpSimples {

    private FabTipoConexaoRest tipoConexao;
    private String enderecoHost;
    private String path;
    private Map<String, String> cabecalhos;
    private String corpo;
    private boolean possuiCorpoComConteudo;

    public FabTipoConexaoRest getTipoConexao() {
        return tipoConexao;
    }

    public ChamadaHttpSimples setTipoConexao(FabTipoConexaoRest tipoConexao) {
        this.tipoConexao = tipoConexao;
        return this;
    }

    public String getEnderecoHost() {
        return enderecoHost;
    }

    public ChamadaHttpSimples setEnderecoHost(String enderecoHost) {
        this.enderecoHost = enderecoHost;
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getCabecalhos() {
        return cabecalhos;
    }

    public ChamadaHttpSimples setCabecalhos(Map<String, String> cabecalhos) {
        this.cabecalhos = cabecalhos;
        return this;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
        if (corpo != null) {
            possuiCorpoComConteudo = true;
        }
    }

    public String getUrlRequisicao() {
        if (getPath() != null) {
            return getEnderecoHost() + getPath();
        } else {
            return getEnderecoHost();
        }

    }

    public boolean isPossuiCorpoComConteudo() {
        return possuiCorpoComConteudo;
    }

    public void setPossuiCorpoComConteudo(boolean possuiCorpoComConteudo) {
        this.possuiCorpoComConteudo = possuiCorpoComConteudo;
    }

}
