/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient;

/**
 *
 * @author novy
 */
public enum FabTipoCodigoRetornoHttp {

    INFORMATIVO,
    SUCESSO,
    REDIRECIONAMENTO,
    ACESSO_NEGADO,
    ERRO_DO_SERVICO,
    FALHA_DE_CONEXAO;

    public static FabTipoCodigoRetornoHttp getTipoCodigoBycod(int codigo) {
        if (codigo == 0) {
            return FALHA_DE_CONEXAO;
        }
        if (codigo >= 100 && codigo <= 199) {
            return INFORMATIVO;
        }
        if (codigo >= 200 && codigo <= 299) {
            return SUCESSO;
        }
        if (codigo >= 300 && codigo <= 399) {
            return REDIRECIONAMENTO;
        }
        if (codigo >= 400 && codigo <= 499) {
            return ACESSO_NEGADO;
        }
        if (codigo >= 500 && codigo <= 599) {
            return ERRO_DO_SERVICO;
        }
        return FALHA_DE_CONEXAO;

    }

}
