/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author salvio
 */
public class ChamadaHttpSimplesGet extends ChamadaHttpSimples {

    public ChamadaHttpSimplesGet(String pUrl) throws MalformedURLException {
        super();
        URL url = new URL(pUrl);
        setPath(url.getPath());
        System.out.println(url.getPort());
        if (url.getPort() > 0) {
            setEnderecoHost(url.getProtocol() + "://" + url.getHost() + ":" + url.getPort());
        } else {
            setEnderecoHost(url.getProtocol() + "://" + url.getHost());
        }
        setPossuiCorpoComConteudo(false);
        setTipoConexao(FabTipoConexaoRest.GET);

    }

}
