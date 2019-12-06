package com.jindanupajit.plugins.org.springframework.boot.binder;

import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
// POST, GET, DELETE, OPTION, PATCH, PUT, TRACE
public class AutoConfiguration {
    public static void webSecurity(HttpSecurity http, RequestMappingHandlerMapping mapping, List<Binder> binderList) {

        for (Binder binder : binderList) {
            System.out.println("Binder: "+binder.getClass().getName());
            ActionMapping[] actionMappings = binder.getClass().getDeclaredAnnotationsByType(ActionMapping.class);

            for (ActionMapping actionMapping : actionMappings) {
                for (ActionType actionType : actionMapping.Action()) {
                    System.out.printf("Action %s -> %s (%s) \n",actionType.name(), actionMapping.Url(), actionMapping.Method());
                }

            }
        }

        for (RequestMappingInfo requestMappingInfo : mapping.getHandlerMethods().keySet()) {

            HandlerMethod handlerMethod = mapping.getHandlerMethods().get(requestMappingInfo);
            System.out.println("Method: "+handlerMethod.getMethod().getDeclaringClass().getName()+"::"+handlerMethod.getMethod().getName()+"()");

            requestMappingInfo.getPatternsCondition().getPatterns().forEach(System.out::println);
            requestMappingInfo.getMethodsCondition().getMethods().forEach(System.out::println);


        }
    }
}
