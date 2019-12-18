/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servletRecepcaoTokenOauth;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteRest;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.TIPO_PARTE_URL;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.TipoClienteOauth;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.ItfFabUrlServletSBFW;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.parametrosURL.InfoParametroURL;

import java.io.Serializable;

/**
 *
 * @author SalvioF
 */
public enum FabUrlServletRecepcaoOauth implements ItfFabUrlServletSBFW, Serializable {

    @InfoParametroURL(nome = "Nome do Parametro", tipoParametro = TIPO_PARTE_URL.TEXTO)
    NOME_PARAMETRO,
    @InfoParametroURL(nome = "Tipo Autenticacao", tipoParametro = TIPO_PARTE_URL.OBJETO_COM_CONSTRUCTOR, fabricaObjetosRelacionada = FabTipoAgenteClienteRest.class, tipoEntidade = TipoClienteOauth.class)
    TIPO_CLIENTE_OAUTH,
    @InfoParametroURL(nome = "Identificador Api", tipoParametro = TIPO_PARTE_URL.TEXTO)
    IDENTIFICADOR_API,

}
