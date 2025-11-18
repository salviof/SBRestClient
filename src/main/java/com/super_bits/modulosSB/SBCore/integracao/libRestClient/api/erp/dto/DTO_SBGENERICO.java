/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.EntidadeSimples;
import jakarta.json.JsonObject;
import java.util.List;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class DTO_SBGENERICO<T extends ItfDTOSBJSON> extends EntidadeSimples implements ItfDTOSBJSON {

    private Map<String, DTO_SBGENERICO> objetosArmazenados;
    private Map<String, List<DTO_SBGENERICO>> listasArmazenadas;
    private JsonObject atributosObjetoJson;
    private boolean modoPojo;
    private final T dtoDecoratorGettersInstanciado;

    @Override
    public Long getId() {
        return (long) getValorPorReflexao();
    }

    @Override
    public String getNome() {
        return (String) getValorPorReflexao();
    }

    public DTO_SBGENERICO(Class<? extends DTO_SB_JSON_PROCESSADOR_GENERICO> pclasseProcessador, String pJson) {
        if (pclasseProcessador == null) {
            modoPojo = true;
            dtoDecoratorGettersInstanciado = null;
        } else {
            modoPojo = false;

            try {
                dtoDecoratorGettersInstanciado = new ObjectMapper().readValue(pJson, (Class<T>) this.getClass());

                ObjectMapper mapper = new ObjectMapper();
                SimpleModule mod = new SimpleModule(pclasseProcessador.getSimpleName());

                try {
                    //   mod.addDeserializer(this.getClass(), pclasseProcessador.newInstance());
                    //    mapper.registerModule(mod);
                    //    dtoDecoratorGettersInstanciado = mapper.readValue(pJson, (Class<T>) this.getClass());
                    return;

                } catch (Throwable t) {
                    System.out.println("UP");
                }
            } catch (Throwable t) {
                System.out.println("UP");
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha processando json em DTO", t);
                throw new UnsupportedOperationException("Falha iniciando DTO");
            }

        }

    }

    protected void setDadosDoObjeto() {

    }

    @Override
    public JsonObject getJsonModoPojo() {
        if (!modoPojo) {
            return dtoDecoratorGettersInstanciado.getJsonModoPojo();
        }
        return atributosObjetoJson;
    }

    public T getObjetoDeserializado() {
        return dtoDecoratorGettersInstanciado;
    }

    @Override
    public List getLista(String pNomeAtributop) {
        if (!modoPojo) {
            return dtoDecoratorGettersInstanciado.getLista(pNomeAtributop);
        }

        if (!listasArmazenadas.containsKey(pNomeAtributop)) {
            return null;
        }
        return listasArmazenadas.get(pNomeAtributop);
    }

    @Override
    public ComoEntidadeSimples getObjeto(String pNomeAtributop) {
        if (!modoPojo) {
            return dtoDecoratorGettersInstanciado.getObjeto(pNomeAtributop);
        }
        if (!objetosArmazenados.containsKey(pNomeAtributop)) {
            return null;
        }
        return objetosArmazenados.get(pNomeAtributop);
    }

    protected void setDadosDoObjeto(JsonObject pJson, Map<String, DTO_SBGENERICO> pObjetosArmazenados, Map<String, List<DTO_SBGENERICO>> pListasArmazenadas) {
        objetosArmazenados = pObjetosArmazenados;
        atributosObjetoJson = pJson;
        listasArmazenadas = pListasArmazenadas;
    }
}
