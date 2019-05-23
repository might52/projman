package org.might.projman;

import org.might.projman.controllers.annotations.AuthHandlerInterceptorAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
                if (response.getStatus() == 404) {
                    response.sendRedirect("/main/not_found");
                }
                return true;
            }
        });
    }
}
