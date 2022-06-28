/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.super_bits.modulosSB.SBCore.integracao.libRestClient.conexao.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.TrustAllStrategy;

/**
 *
 * @author salvio
 */
public class EstrategiaConfiarEmTodos extends TrustAllStrategy {

    public static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {

        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }
    };

    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        boolean confiavel = super.isTrusted(chain, authType);
        if (!confiavel) {
            System.out.println("a conexão não é confiavel, mesmo utilizando a estrategia confiar em tudo");
        }
        return true;
    }

}
