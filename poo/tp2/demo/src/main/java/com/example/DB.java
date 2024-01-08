package com.example;

/**
 * DB
 */
public interface DB<T> {
  String url = "jdbc:mysql://62.72.62.1:3306/u780383842_tp2?useSSL=true&requireSSL=true&verifyServerCertificate=false&enabledSSLCipherSuites=TLS_DHE_RSA_WITH_AES_256_CBC_SHA256&enabledTLSProtocols=TLSv1.2";
  

  String[] get();

  int insert(T obj);
}