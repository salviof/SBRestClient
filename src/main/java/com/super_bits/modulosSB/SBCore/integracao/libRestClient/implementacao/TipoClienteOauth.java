/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeVinculadoAEnum;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;

/**
 *
 * @author SalvioF
 */
@InfoObjetoSB(plural = "tipo Clientes", tags = {"Tipo Cliente"}, fabricaVinculada = FabTipoAgenteClienteApi.class)
public class TipoClienteOauth extends ItemSimples implements ComoEntidadeVinculadoAEnum {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nome;
    private FabTipoAgenteClienteApi enumVinculado;

    @Override
    public void setEnumVinculado(ComoFabrica pFabrica) {
        enumVinculado = (FabTipoAgenteClienteApi) pFabrica;
    }

    @Override
    public FabTipoAgenteClienteApi getEnumVinculado() {
        return enumVinculado;
    }

}
