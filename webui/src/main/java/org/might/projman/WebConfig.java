package org.might.projman;

import org.might.projman.controllers.annotations.AuthHandlerInterceptorAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private final UserPreference userPreference;

    @Autowired
    public WebConfig(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AuthHandlerInterceptorAdapter(userPreference));
    }

}
