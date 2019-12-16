/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sfurbino
 * @since 10/12/2019
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InfoConfigRestClientIntegracao {

    public String nomeIntegracao();

    public String[] enderecosDocumentacao() default {};

    public FabTipoAutenticacaoRest tipoAutenticacao();

    public Class<? extends ItfFabConfigModulo> configuracao();

}
