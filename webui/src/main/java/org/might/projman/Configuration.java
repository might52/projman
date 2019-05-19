package org.might.projman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@org.springframework.context.annotation.Configuration

public class Configuration {

    @Bean("userPreference")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserPreference userPreference() {
        System.out.println("create user preference");
        return new UserPreference();
    }

}
