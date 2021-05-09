package com.ws.wsclient.commons.config;

import be.fgov.ehealth.genericinsurability.protocol.v1.GenericInsurabilityPortType;
import be.fgov.ehealth.sts.protocol.v1.Saml11TokenServicePortType;
import com.ws.wsclient.adapter.GenericInsurabilityServiceAdapter;
import com.ws.wsclient.adapter.Saml11TokenServiceAdapter;
import com.ws.wsclient.commons.spring.CustomJaxWsPortProxyFactoryBean;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.AbstractLoggingInterceptor;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HttpConduitFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public  class CXFConfig2 {

    @Value("${tracing.context.active:true}")
    private boolean tracingContextActive;

    @Value("${enableSchemaValidation:false}")
    private boolean enableSchemaValidation;

    @Bean
    @Lazy
    public Saml11TokenServiceAdapter saml11TokenServiceAdapter(
            @Value("${ws.endpoint.Saml11TokenService}") String serviceAddress) {
        return createServiceProxy(
                Saml11TokenServiceAdapter.class,
                Saml11TokenServicePortType.class,
                serviceAddress);
    }

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
    @Bean
    public AbstractLoggingInterceptor logInInterceptor() {
        LoggingInInterceptor logInInterceptor = new LoggingInInterceptor();
//        logInInterceptor.setPrettyLogging(true);
        return logInInterceptor;
    }
    @Bean
    public AbstractLoggingInterceptor logOutInterceptor() {
        LoggingOutInterceptor logOutInterceptor = new LoggingOutInterceptor();
//        logOutInterceptor.setPrettyLogging(false);
        return logOutInterceptor;
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        LoggingFeature logFeature = new LoggingFeature();
        logFeature.setPrettyLogging(true);
        logFeature.initialize(springBus);
        springBus.getFeatures().add(logFeature);
        springBus.getInInterceptors().add(logInInterceptor());
        springBus.getInFaultInterceptors().add(logInInterceptor());
        springBus.getOutInterceptors().add(logOutInterceptor());
        springBus.getOutFaultInterceptors().add(logOutInterceptor());
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
        return factoryBean;
    }
}
