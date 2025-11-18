/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.repositorioLinkEntidades;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfServicoLinkDeEntidadesERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimplesSomenteLeitura;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author sfurbino
 */
public class RepositorioLinkEntidadesGenerico implements ItfServicoLinkDeEntidadesERP {

    private static final Map<String, Map<String, String>> mapaLigacaoEstatico = new HashMap<>();

    @Override
    public String getCodigoApiExterna(Class pEntidade, Long pCodigoInterno) {
        if (SBCore.isEmModoDesenvolvimento()) {
            if (mapaLigacaoEstatico.containsKey(pEntidade.getSimpleName())) {
                return mapaLigacaoEstatico.get(pEntidade.getSimpleName()).get(Long.toString(pCodigoInterno));
            }
            return null;
        } else {
            return null;
            //throw new UnsupportedOperationException("O link de entidades persistido não foi implementado");
        }
    }

    @Override
    public String getCodigoSistemaInterno(Class pEntidade, int pCodigoapiExterno) {
        if (SBCore.isEmModoDesenvolvimento()) {
            if (mapaLigacaoEstatico.containsKey(pEntidade.getSimpleName())) {
                if (mapaLigacaoEstatico.get(pEntidade.getSimpleName()).containsValue((Integer.toString(pCodigoapiExterno)))) {
                    Optional<Map.Entry<String, String>> chaveValor = mapaLigacaoEstatico.get(pEntidade.getSimpleName()).entrySet().stream().filter(et -> et.getValue().equals(Integer.toString(pCodigoapiExterno))).findFirst();
                    return chaveValor.get().getKey();
                }

            }
            return null;
        } else {
            return null;
            //throw new UnsupportedOperationException("O link de entidades persistido não foi implementado");
        }
    }

    @Override
    public boolean registrarCodigoLigacaoApi(Class pEntidade, Long codigoInterno, String codigoAPIExterna) {
        if (SBCore.isEmModoDesenvolvimento()) {
            if (!mapaLigacaoEstatico.containsKey(pEntidade.getSimpleName())) {
                mapaLigacaoEstatico.put(pEntidade.getSimpleName(), new HashMap<>());
            }
            mapaLigacaoEstatico.get(pEntidade.getSimpleName()).put(String.valueOf(codigoInterno), codigoAPIExterna);
            return true;
        }
        return false;
        //throw new UnsupportedOperationException("O link de entidades persistido não foi implementado");

    }

    @Override
    @Deprecated
    public <T extends ComoEntidadeSimplesSomenteLeitura> T getObjetoDTOFromJson(Class<? extends T> pClass, String Json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
