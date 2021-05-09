/*package com.ws.wsclient.commons.config;

import be.fgov.ehealth.consultrn.searchpersonbyssin.v1_0.SearchPersonBySSINPortType;
import be.fgov.ehealth.genericinsurability.protocol.v1.GenericInsurabilityPortType;
import com.ws.wsclient.adapter.SearchPersonBySSINServiceAdapter;
import com.ws.wsclient.commons.handler.SoapTracingHandler;
import com.ws.wsclient.commons.spring.CustomJaxWsPortProxyFactoryBean;
import com.ws.wsclient.adapter.GenericInsurabilityServiceAdapter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.configuration.security.KeyStoreType;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.http.HttpConduitFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public  class CXFConfig {

    @Value("${tracing.context.active:true}")
    private boolean tracingContextActive;

    @Value("${enableSchemaValidation:false}")
    private boolean enableSchemaValidation;

    @Bean
    @Lazy
    public GenericInsurabilityServiceAdapter GenericInsurabilityServiceAdapter(
            @Value("${ws.endpoint.GenericInsurabilityService}") String serviceAddress) {
        return createServiceProxy(
                GenericInsurabilityServiceAdapter.class,
                GenericInsurabilityPortType.class,
                serviceAddress);
    }

    @Bean
    @Lazy
    public SearchPersonBySSINServiceAdapter SearchPersonBySSINServiceAdapter(
            @Value("${ws.endpoint.SearchPersonBySSINService}") String serviceAddress) {
        return createServiceProxy(
                SearchPersonBySSINServiceAdapter.class,
                SearchPersonBySSINPortType.class,
                serviceAddress);
    }


    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setRejectedExecutionHandler(generalCallerRunsPolicy());
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(50);
        threadPoolTaskExecutor.setQueueCapacity(0);
        return threadPoolTaskExecutor;
    }
    @Bean
    public ThreadPoolExecutor.CallerRunsPolicy generalCallerRunsPolicy(){
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        Map<String, Object> properties = new HashMap<>();
        properties.put("org.apache.cxf.logging.FaultListener", new SoapTracingHandler());
        springBus.setProperties(properties);
        return springBus;
    }

    protected <T> T createServiceProxy(Class<T> serviceInterfaceClass, Class<?> stubsClass, String serviceAddress) {
        return this.createServiceProxy(serviceInterfaceClass, stubsClass, serviceAddress, null);
    }

    protected <T> T createServiceProxy(Class<T> serviceInterfaceClass, Class<?> stubsClass, String serviceAddress, HttpConduitFeature httpConduitFeature) {
        CustomJaxWsPortProxyFactoryBean<T> factoryBean = new CustomJaxWsPortProxyFactoryBean<>();
        factoryBean.setServiceInterface(serviceInterfaceClass);
        factoryBean.setJaxWsProxyFactoryBean(createClientFactory(
                stubsClass,
                serviceAddress,
                httpConduitFeature));
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    protected <T> JaxWsProxyFactoryBean createClientFactory(Class<T> serviceClass,
                                                            String address, HttpConduitFeature conduitFeature) {
        JaxWsProxyFactoryBean createClientFactory = createClientFactory(serviceClass, address);
        if (conduitFeature != null) {
            createClientFactory.setFeatures(Collections.singletonList(conduitFeature));
        }
        createClientFactory.getHandlers().add(new SoapTracingHandler(address));
        createClientFactory.setBus(springBus());
        return createClientFactory;
    }

    protected <T> JaxWsProxyFactoryBean createClientFactory(Class<T> serviceClass, String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(serviceClass);
        factoryBean.setAddress(address);
        Map<String, Object> properties = ObjectUtils.defaultIfNull(factoryBean.getProperties(), new HashMap<>());
        properties.put("set-jaxb-validation-event-handler", enableSchemaValidation);
        factoryBean.setProperties(properties);
        factoryBean.getInInterceptors().add(new GZIPInInterceptor());
        factoryBean.getInFaultInterceptors().add(new GZIPInInterceptor());
        return factoryBean;
    }

    protected KeyStoreType createKeyStoreType(
            String fileURIString,
            String keystoreTypeString,
            String keystorePassword) {
        KeyStoreType keyStoreType = new KeyStoreType();
        keyStoreType.setFile(fileURIString);
        keyStoreType.setType(keystoreTypeString);
        keyStoreType.setPassword(keystorePassword);
        return keyStoreType;
    }
}
*/