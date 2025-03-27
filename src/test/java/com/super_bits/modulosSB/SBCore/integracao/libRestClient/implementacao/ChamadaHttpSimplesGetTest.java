/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
