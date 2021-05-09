package com.ws.wsclient.commons.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Factory bean that allows to add an interface before a JAX-WS proxy service. This allows to ignore certain exceptions.
 * ( based on functionality provided by serviceInterface attribute of
 * org.springframework.remoting.jaxrpc.JaxRpcPortProxyFactoryBean )
 * <p>
 * Method arguments can also be added automatically by annotating interfaces with {@link AdaptInvocationArguments} and
 * by setting up one or more {@link MethodInvocationArgumentsAdapter} in the application context.
 *
 */
public class CustomJaxWsPortProxyFactoryBean<T> implements FactoryBean<T>, InitializingBean, MethodInterceptor {

    private Class<?> serviceInterface;

    private Object client;

    private T proxy;

    private Map<Class<? extends MethodInvocationArgumentsAdapter>, MethodInvocationArgumentsAdapter> methodInvocationArgumentsAdapters = new HashMap<>();

    private Map<Method, List<MethodInvocationArgumentsAdapter>> invocationAdaptersByMethods = new HashMap<>();

    public Object getClient() {
        return client;
    }

    /**
     * creates the client from a JaxWsProxyFactoryBean
     *
     * @param clientFactory factory bean
     */
    public void setJaxWsProxyFactoryBean(JaxWsProxyFactoryBean clientFactory) {
        this.client = clientFactory.create();
    }

    public void setClient(Object client) {
        this.client = client;
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public T getObject() {
        return this.proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * TODO check of a typesafe alternative
     */
    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() {
        ProxyFactory pf = new ProxyFactory();
        pf.addInterface(this.serviceInterface);
        pf.addAdvice(this);
        this.proxy = (T) pf.getProxy(ClassUtils.getDefaultClassLoader());

    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Class<?>[] parameterTypes = method.getParameterTypes();

        try {

            AdaptInvocationArguments adaptInvocationArguments = invocation.getMethod().getAnnotation(
                    AdaptInvocationArguments.class);
            if (adaptInvocationArguments != null) {
                List<MethodInvocationArgumentsAdapter> invocationAdaptersForMethod = invocationAdaptersByMethods
                        .get(method);
                if (invocationAdaptersForMethod == null) {

                    invocationAdaptersForMethod = new ArrayList<>();
                    for (Class<? extends MethodInvocationArgumentsAdapter> adapterClass : adaptInvocationArguments
                            .adapters()) {
                        MethodInvocationArgumentsAdapter adapter = methodInvocationArgumentsAdapters.get(adapterClass);
                        if (adapter == null) {
                            throw new IllegalStateException("Found no MethodInvocationArgumentsAdapter with type "
                                    + adapterClass + " configured for proxy of " + serviceInterface);
                        }
                        invocationAdaptersForMethod.add(adapter);
                    }
                    invocationAdaptersByMethods.put(method, invocationAdaptersForMethod);
                }
                List<Class<?>> parameterTypeList = new ArrayList<>(Arrays.asList(parameterTypes));
                List<Object> argumentList = new ArrayList<>(Arrays.asList(arguments));
                for (MethodInvocationArgumentsAdapter adapter : invocationAdaptersForMethod) {
                    adapter.adapt(method, parameterTypeList, argumentList);
                }
                parameterTypes = parameterTypeList.toArray(new Class<?>[]{});
                arguments = argumentList.toArray();

            }

            Method stubMethod = client.getClass().getMethod(method.getName(), parameterTypes);

            return invoke(stubMethod, arguments);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            // FIXME RemoteAccessException might not be the best exception to rethrow...
            if (cause == null) {
                throw new RemoteAccessException("Could not access remote service [" + serviceInterface.getName() + "]",
                        ex);
            } else if (ReflectionUtils.declaresException(method, cause.getClass())) {
                throw cause;
            } else {
                throw new RemoteAccessException(
                        "Could not access remote service [" + serviceInterface.getName() + "] : " + cause.getMessage(),
                        cause);
            }
        } catch (NoSuchMethodException ex) {
            throw new RemoteProxyFailureException("No matching method found for: " + method, ex);
        } catch (Exception ex) {
            throw new RemoteProxyFailureException("Invocation of method failed: " + method, ex);
        }
    }

    /**
     * Invokes the method on the client (with given arguments). (protected method for testability)
     *
     * @param method    method
     * @param arguments arguments
     * @return resulting object
     * @throws IllegalAccessException    method is not accessible
     * @throws InvocationTargetException invocation fails
     */
    private Object invoke(Method method, Object[] arguments)
            throws IllegalAccessException, InvocationTargetException {

        return method.invoke(client, arguments);
    }

    /**
     * returns the adapters
     *
     * @return adapters
     */
    public List<MethodInvocationArgumentsAdapter> getAdapters() {
        return new ArrayList<>(this.methodInvocationArgumentsAdapters.values());
    }

    /**
     * adds the adapters
     *
     * @param methodInvocationArgumentsAdapters method invocation argument adapter
     */
    public void setAdapters(List<MethodInvocationArgumentsAdapter> methodInvocationArgumentsAdapters) {

        for (MethodInvocationArgumentsAdapter adapter : methodInvocationArgumentsAdapters) {
            this.methodInvocationArgumentsAdapters.put(adapter.getClass(), adapter);
        }
    }

}