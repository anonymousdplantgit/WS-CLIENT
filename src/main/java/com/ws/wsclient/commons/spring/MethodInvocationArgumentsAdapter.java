package com.ws.wsclient.commons.spring;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Adapter for proxied method call arguments.

 */
public interface MethodInvocationArgumentsAdapter {

    /**
     * Adapts the argument of a method invocation.
     *
     * @param invokedMethod  the invoked method
     * @param parameterTypes the types of the params.
     * @param arguments      the argument of the invocation.
     */
    void adapt(Method invokedMethod, List<Class<?>> parameterTypes, List<Object> arguments);

}
