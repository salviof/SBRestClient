/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfMensagem;
import java.util.List;

import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author SalvioF
 */
public class RespostaWebServiceRestIntegracao implements ItfRespostaWebServiceSimples, ItfResposta {

    private RespostaWebServiceSimples respostaWs;

    public RespostaWebServiceRestIntegracao(RespostaWebServiceSimples pResposta) {
        respostaWs = pResposta;
    }

    public RespostaWebServiceRestIntegracao(String pResposta, int codigoResposta) {
        this(pResposta, codigoResposta, null);

    }

    public RespostaWebServiceRestIntegracao(String pResposta, int codigoResposta, String erroUsuario) {
        respostaWs = new RespostaWebServiceSimples(codigoResposta, pResposta, erroUsuario);

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
            if (respostaWs.isSucesso()) {
                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse((String) respostaWs.getResposta());
            } else {
                return null;
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro interpretando Json" + t.getMessage(), t);
            return null;
        }
    }

    @Override
    public JSONObject getRespostaComoObjetoJson() {
        return respostaWs.getRespostaComoObjetoJson();
    }

    @Override
    public String getRespostaTexto() {
        return respostaWs.getRespostaTexto();
    }

    @Override
    public Resultado getResultado() {
        return respostaWs.getResultado();
    }

    @Override
    public List<ItfMensagem> getMensagens() {
        return respostaWs.getMensagens();
    }

    @Override
    public Class getTipoRetorno() {
        return respostaWs.getTipoRetorno();
    }

    @Override
    public Object getRetorno() {
        return respostaWs.getRetorno();
    }

    @Override
    @Deprecated
    public ItfResposta setRetornoDisparaERetorna(Object pObjetoResultante) {
        return respostaWs.setRetornoDisparaERetorna(pObjetoResultante);
    }

    @Override
    public ItfResposta setRetorno(Object pObjetoResultante) {
        return respostaWs.setRetorno(pObjetoResultante);
    }

    @Override
    public ItfResposta addMensagemAvisoDisparaERetorna(String pMensagem) {
        return respostaWs.addMensagemAlertaDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta addMensagemDisparaERetorna(ItfMensagem pMensagem) {
        return respostaWs.addMensagemDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta addMensagemErroDisparaERetorna(String pMensagem) {
        return respostaWs.addMensagemErroDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta addMensagemAlertaDisparaERetorna(String pMensagem) {
        return respostaWs.addMensagemAlertaDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta dispararMensagens() {
        return respostaWs.dispararMensagens();
    }

    @Override
    public ItfResposta addMensagem(ItfMensagem pMensagem) {
        return respostaWs.addMensagem(pMensagem);
    }

    @Override
    public ItfResposta addAlerta(String Pmensagem) {
        return respostaWs.addAlerta(Pmensagem);
    }

    @Override
    public ItfResposta addAviso(String Pmensagem) {
        return respostaWs.addAviso(Pmensagem);
    }

    @Override
    public ItfResposta addErro(String Pmensagem) {
        return respostaWs.addErro(Pmensagem);
    }

    @Override
    public boolean isTemAlerta() {
        return respostaWs.isTemAlerta();
    }

    @Override
    public boolean isSucesso() {
        return respostaWs.isSucesso();
    }

    @Override
    public String getUrlDestino() {
        return respostaWs.getUrlDestino();
    }

    @Override
    public boolean isTemUrlDestino() {
        return respostaWs.isTemUrlDestino();
    }

    @Override
    public void setUrlDestino(String pDestino) {
        respostaWs.setUrlDestino(pDestino);
    }

    @Override
    public void setUrlDestinoSucesso(String pDestinoSucesso) {
        respostaWs.setUrlDestinoSucesso(pDestinoSucesso);
    }

    @Override
    public void setUrlDestinoFalha(String pDestinoFalha) {
        respostaWs.setUrlDestinoFalha(pDestinoFalha);
    }

    @Override
    public int getCodigoResposta() {
        return respostaWs.getCodigoResposta();
    }

}
