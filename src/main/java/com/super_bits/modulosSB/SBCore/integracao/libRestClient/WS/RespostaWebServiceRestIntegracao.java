/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabCodigosRetornoHttp;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;

import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author SalvioF
 */
public class RespostaWebServiceRestIntegracao extends RespostaWebServiceSimples {

    public RespostaWebServiceRestIntegracao(String pResposta, int codigoResposta) {
        this(pResposta, codigoResposta, null);

    }

    public RespostaWebServiceRestIntegracao(String pResposta, int codigoResposta, String erroUsuario) {
        super(codigoResposta, pResposta, erroUsuario);

        if (UtilSBCoreStringValidador.isNuloOuEmbranco(pResposta) || UtilSBCoreStringValidador.isNAO_NuloNemBranco(erroUsuario) || (codigoResposta < 200 || codigoResposta > 299)) {

            if (UtilSBCoreStringValidador.isNAO_NuloNemBranco(erroUsuario)) {
                addErro(erroUsuario);
            } else {
                FabCodigosRetornoHttp codigo = FabCodigosRetornoHttp.getCodigoByNumero(codigoResposta);
                if (codigo != null) {
                    if (codigoResposta >= 200 && codigoResposta < 300) {
                        addAlerta(codigo.getDescricao());
                    }
                    addErro(codigo.getDescricao());
                } else {
                    addErro("Falha de comunicação");
                }
            }
        } else {

            setRetorno(pResposta);
        }
    }

    /**
     *
     * Retorna a resposta no formato do objeto solicitado
     *
     * O parametro mapeamento deve ser enviado na sequencia: CaminhoAtributo1,
     * CaminhoFOnteJson1, CaminhoAtributo2, CaminhoFOnteJson2, ...
     *
     * @param <T>
     * @param pClasse
     * @param mapeamento
     * @return
     */
    public <T> T getRespostaAplicandoParametros(Class<T> pClasse, String mapeamento) {

        return null;
    }

    public JSONObject getJsonObj() {
        try {
            if (isSucesso()) {
                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse((String) getRetorno());
            } else {
                return null;
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro interpretando Json" + t.getMessage(), t);
            return null;
        }
    }

}
