package org.might.projman.controllers.annotations;

import org.might.projman.UserPreference;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private final UserPreference userPreference;

    private static final String LOGIN_REDIRECT = "/login";

    public AuthHandlerInterceptorAdapter(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Class<?> beanClass = ((HandlerMethod)handler).getBean().getClass();
            if (beanClass.isAnnotationPresent(Auth.class)) {
                if (!validateSession()) {
                    response.sendRedirect(LOGIN_REDIRECT);
                }
            }
        }
        return true;
    }

    private boolean validateSession() {
        return userPreference != null && userPreference.getUserID() != 0 && !userPreference.getUserLogin().isEmpty();
    }
}
