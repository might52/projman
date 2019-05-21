package org.might.projman;

import org.might.projman.controllers.ProjectRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean("userPreference")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserPreference userPreference() {
        return new UserPreference();
    }

}
