package org.might.projman;

import org.might.projman.controllers.AuthHandlerBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean("userPreference")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserPreference userPreference() {
        return new UserPreference();
    }

    @Bean("authHendlerBeanPostProcessor")
    @Scope(value = SCOPE_SINGLETON)
    public AuthHandlerBeanPostProcessor authHendlerBeanPostProcessor() {
        return new AuthHandlerBeanPostProcessor();
    }

}
