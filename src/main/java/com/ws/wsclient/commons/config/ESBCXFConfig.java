//package com.ws.wsclient.commons.config;
//
//import com.ws.wsclient.exception.TechnicalErrorException;
//import org.apache.cxf.configuration.jsse.TLSClientParameters;
//import org.apache.cxf.configuration.jsse.TLSClientParametersConfig;
//import org.apache.cxf.configuration.security.KeyManagersType;
//import org.apache.cxf.configuration.security.KeyStoreType;
//import org.apache.cxf.configuration.security.TLSClientParametersType;
//import org.apache.cxf.configuration.security.TrustManagersType;
//import org.apache.cxf.transport.http.HttpConduitConfig;
//import org.apache.cxf.transport.http.HttpConduitFeature;
//import org.apache.cxf.transports.http.configuration.ConnectionType;
//import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//@Configuration
//public class ESBCXFConfig extends CXFConfig2 {
//    @Value("${ws.ssl.esb.keystore.pw}")
//    private String esbKeystorePassword;
//
//    @Value("${ws.ssl.esb.keystore.file}")
//    private String esbKeystoreFileURIString;
//
//    @Value("${ws.ssl.esb.keystore.type}")
//    private String esbKeystoreType;
//
//    @Value("${ws.ssl.esb.truststore.file}")
//    private String esbTruststoreFileURIString;
//
//    @Value("${ws.ssl.esb.truststore.type}")
//    private String esbTruststoreType;
//
//    @Value("${ws.ssl.esb.truststore.pw}")
//    private String esbTruststorePassword;
//
//    @Value("${ws.receiveTimeout.esb}")
//    private long wsReceiveTimeout;
//
//    @Value("${ws.connectionTimeout.esb}")
//    private long wsConnectionTimeout;
//
//    @Override
//    protected <T> T createServiceProxy(Class<T> serviceInterfaceClass, Class<?> stubsClass, String serviceAddress) {
//        return super.createServiceProxy(serviceInterfaceClass, stubsClass, serviceAddress, esbConduitFeature());
//    }
//
//    /**
//     * {@link HttpConduitFeature} for all WS passing the ESB
//     */
//    @Bean
//    public HttpConduitFeature esbConduitFeature() {
//        TLSClientParametersType params = new TLSClientParametersType();
//
//        KeyManagersType keyManagersType = new KeyManagersType();
//        keyManagersType.setKeyPassword(esbKeystorePassword);
//        KeyStoreType keyStoreType = createKeyStoreType(esbKeystoreFileURIString, esbKeystoreType, esbKeystorePassword);
//        keyManagersType.setKeyStore(keyStoreType);
//
//        TrustManagersType trustManager = new TrustManagersType();
//        KeyStoreType trustKeyStoreType = createKeyStoreType(esbTruststoreFileURIString, esbTruststoreType,
//                esbTruststorePassword);
//        trustManager.setKeyStore(trustKeyStoreType);
//
//        params.setKeyManagers(keyManagersType);
//        params.setTrustManagers(trustManager);
//        params.setDisableCNCheck(true);
//
//        TLSClientParameters tlsClientParameters;
//        try {
//            tlsClientParameters = TLSClientParametersConfig.createTLSClientParametersFromType(params);
//        } catch (GeneralSecurityException | IOException e) {
//            throw new TechnicalErrorException(e.getMessage(), e);
//        }
//
//        HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
//        clientPolicy.setAutoRedirect(true);
//        clientPolicy.setConnection(ConnectionType.KEEP_ALIVE);
//        clientPolicy.setReceiveTimeout(wsReceiveTimeout);
//        clientPolicy.setConnectionTimeout(wsConnectionTimeout);
//
//        clientPolicy.setAcceptEncoding("gzip");
//
//        HttpConduitConfig config = new HttpConduitConfig();
//        config.setTlsClientParameters(tlsClientParameters);
//        config.setClientPolicy(clientPolicy);
//
//        HttpConduitFeature feature = new HttpConduitFeature();
//        feature.setConduitConfig(config);
//        return feature;
//    }
//    protected KeyStoreType createKeyStoreType(
//            String fileURIString,
//            String keystoreTypeString,
//            String keystorePassword) {
//        KeyStoreType keyStoreType = new KeyStoreType();
//        keyStoreType.setFile(fileURIString);
//        keyStoreType.setType(keystoreTypeString);
//        keyStoreType.setPassword(keystorePassword);
//        return keyStoreType;
//    }
//}
