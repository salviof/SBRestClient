/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient;

import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author SalvioF
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InfoConsumoRestService {

    public String[] parametrosGet() default {};

    @Deprecated
    public String[] parametrosProcessamento() default {};

    public String[] parametrosPost() default {};

    public String urlDocumentacao() default "";

    public FabTipoArquivoImportacao tipoInformacaoRecebida() default FabTipoArquivoImportacao.INDETERMINADO;

    public FabTipoArquivoImportacao tipoInformacaoEnviada() default FabTipoArquivoImportacao.INDETERMINADO;

    public FabTipoConexaoRest tipoConexao() default FabTipoConexaoRest.INDETERMINADO;

    public String getPachServico() default "";

    public String urlServidorAlternativo() default "";

    /**
     * Adiciona o Reader Authorization, valor Bearer {0}
     *
     * @return falso não adiciona o Header
     */
    public boolean adicionarAutenticacaoBearer() default false;

    public boolean aceitarCertificadoDeHostNaoConfiavel() default false;

    public boolean corpoRequisicaoEmBytes() default false;

}
