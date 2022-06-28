/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.conexao.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 *
 * @author salvio
 */
public class HostnameVerifierPromiscuo implements HostnameVerifier {

    @Override
    public boolean verify(String string, SSLSession ssls) {
        return true;
    }

}
