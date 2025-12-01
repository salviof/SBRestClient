/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaSimples;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfMensagem;
import groovy.json.JsonBuilder;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.util.HashMap;
import java.util.List;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

import org.json.simple.parser.JSONParser;

/**
 *
 * @author desenvolvedor
 */
public class RespostaWebServiceSimples implements ItfRespostaWebServiceSimples, ItfResposta {

    private final int codigoResposta;
    private final String resposta;
    private final String respostaErro;
    private final RespostaSimples resp;

    public RespostaWebServiceSimples(int pCodigo, String pResposta, String pRespostaErro) {
        codigoResposta = pCodigo;
        resposta = pResposta;
        respostaErro = pRespostaErro;
        resp = new RespostaSimples(String.class);
        FabTipoCodigoRetornoHttp tipoRetorno = FabTipoCodigoRetornoHttp.getTipoCodigoBycod(pCodigo);

        switch (tipoRetorno) {
            case INFORMATIVO:
                break;
            case SUCESSO:

                break;
            case REDIRECIONAMENTO:
                addErro("Houve solicitação de redirecionamento da url.");

                break;
            case ACESSO_NEGADO:
                addErro("Acesso negado ");
                addErro(respostaErro);
                break;
            case ERRO_DO_SERVICO:
                addErro("O Serviço falhou!");
                try {
                    throw new UnsupportedOperationException("Erro de  de servidor acessando API remota: codigo" + pCodigo);

                } catch (Throwable t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro no servidor " + pResposta + "-" + pRespostaErro, t);
                }
                break;
            case FALHA_DE_CONEXAO:
                addErro("Falha de conexão com API");
                break;
            case RECURSO_NAO_ENCONTRADO:
                addErro("Requisição inválida, recurso não encontrado Código: " + pCodigo);
                break;
            default:
                throw new AssertionError(tipoRetorno.name());

        }

    }

    @Override
    public int getCodigoResposta() {
        return codigoResposta;
    }

    public String getResposta() {
        return resposta;
    }

    public String getRespostaErro() {

        return respostaErro;
    }

    @Override
    public JsonObject getRespostaComoObjetoJson() {
        try {

            return UtilCRCJson.getJsonObjectByTexto((String) getRetorno());

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro interpretando Json" + t.getMessage() + "->" + getRetorno(), t);
            return null;
        }
    }

    @Override
    public JsonArray getRespostaComoObjetoJsonArray() {
        try {

            return UtilCRCJson.getJsonArrayByTexto((String) getRetorno());

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro interpretando Json" + t.getMessage() + "->" + getRetorno(), t);
            return null;
        }
    }

    @Override
    public String getRespostaTexto() {
        if (UtilCRCStringValidador.isNAO_NuloNemBranco(respostaErro)) {
            return respostaErro;
        } else {
            return resposta;
        }
    }

    @Override
    public Resultado getResultado() {
        return resp.getResultado();
    }

    @Override
    public List<ItfMensagem> getMensagens() {
        return resp.getMensagens();
    }

    @Override
    public Class getTipoRetorno() {
        return resp.getTipoRetorno();
    }

    @Override
    public Object getRetorno() {
        if (resp.getRetorno() == null) {
            return getRespostaTexto();
        } else {
            return resp.getRetorno();
        }

    }

    @Override
    @Deprecated
    public ItfResposta setRetornoDisparaERetorna(Object pObjetoResultante) {
        return resp.setRetornoDisparaERetorna(pObjetoResultante);
    }

    @Override
    public ItfResposta setRetorno(Object pObjetoResultante) {
        return resp.setRetorno(pObjetoResultante);
    }

    @Override
    public ItfResposta addMensagemAvisoDisparaERetorna(String pMensagem) {
        return resp.addMensagemAvisoDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta addMensagemDisparaERetorna(ItfMensagem pMensagem) {
        return resp.addMensagemDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta addMensagemErroDisparaERetorna(String pMensagem) {
        return resp.addMensagemErroDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta addMensagemAlertaDisparaERetorna(String pMensagem) {
        return resp.addMensagemAvisoDisparaERetorna(pMensagem);
    }

    @Override
    public ItfResposta dispararMensagens() {
        return resp.dispararMensagens();
    }

    @Override
    public ItfResposta addMensagem(ItfMensagem pMensagem) {
        return resp.addMensagem(pMensagem);
    }

    @Override
    public final ItfResposta addAlerta(String Pmensagem) {
        return resp.addAlerta(Pmensagem);
    }

    @Override
    public ItfResposta addAviso(String Pmensagem) {
        return resp.addAviso(Pmensagem);
    }

    @Override
    public final ItfResposta addErro(String Pmensagem) {
        return resp.addErro(Pmensagem);
    }

    @Override
    public boolean isTemAlerta() {
        return resp.isTemAlerta();
    }

    @Override
    public boolean isSucesso() {
        return resp.isSucesso();
    }

    @Override
    public String getUrlDestino() {
        return resp.getUrlDestino();
    }

    @Override
    public boolean isTemUrlDestino() {
        return resp.isTemUrlDestino();
    }

    @Override
    public void setUrlDestino(String pDestino) {
        resp.setUrlDestino(pDestino);
    }

    @Override
    public void setUrlDestinoSucesso(String pDestinoSucesso) {
        resp.setUrlDestinoSucesso(pDestinoSucesso);
    }

    @Override
    public void setUrlDestinoFalha(String pDestinoFalha) {
        resp.setUrlDestinoFalha(pDestinoFalha);
    }

}
