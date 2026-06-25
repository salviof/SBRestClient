package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author salvio
 */
public class ChamadaHttpSimplesGetTest {

    public ChamadaHttpSimplesGetTest() {
    }

    @Test
    public void testSomeMethod() {
        try {
            ChamadaHttpSimplesGet chamada = new ChamadaHttpSimplesGet("https://indices-ipca-igpm.vercel.app/api/indices");
            System.out.println(chamada.getUrlRequisicao());
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChamadaHttpSimplesGetTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
