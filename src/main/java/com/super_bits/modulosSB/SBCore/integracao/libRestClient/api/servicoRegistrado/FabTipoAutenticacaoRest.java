/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.InfoPropriedadeConfigRestIntegracao;

/**
 *
 * @author sfurbino
 */
public enum FabTipoAutenticacaoRest {

    OAUTHV1,
    OAUTHV2,
    USUARIO_SENHA_SIMPLES,
    CHAVE_ACESSO_METODOLOGIA_PROPRIA,
    CHAVE_PUBLICA_PRIVADA;

}
