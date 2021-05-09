package com.ws.wsclient.commons.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that the annotated method has only a proxy
 * implementation and that while being proxied, arguments might be
 * adapted by one or more {@link MethodInvocationArgumentsAdapter}.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdaptInvocationArguments {

    /**
     * handlers to use to adapt params for targeted method invocation
     */
    Class<? extends MethodInvocationArgumentsAdapter>[] adapters();

}
