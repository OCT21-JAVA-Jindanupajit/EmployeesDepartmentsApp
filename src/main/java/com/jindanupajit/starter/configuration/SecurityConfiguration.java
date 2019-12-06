package com.jindanupajit.starter.configuration;

import com.jindanupajit.plugins.org.springframework.boot.binder.AutoConfiguration;
import com.jindanupajit.plugins.org.springframework.boot.binder.Binder;
import com.jindanupajit.starter.service.PasswordEncoder;
import com.jindanupajit.starter.service.UserDetailsServiceImpl;
import com.jindanupajit.starter.util.Verbose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private List<Binder> binderList;

    @Value("${spring.h2.console.enabled}")
    private boolean h2ConsoleEnabled;

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        Verbose.printlnf("set userDetailsService");
        auth    .userDetailsService(userDetailsService)
                .passwordEncoder(PasswordEncoder.getInstance());

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        Verbose.printlnf("set HttpSecurity");

        AutoConfiguration.webSecurity(httpSecurity, requestMappingHandlerMapping, binderList);

        httpSecurity    .authorizeRequests()
                .antMatchers(
                        "/",
                        "/login",
                        "/logout",
                        "/js/**",
                        "/css/**",
                        "/images/**",
                        "/search/**",
                        "/employee/search",
                        "/department/search"
                )
                .permitAll();

        if (h2ConsoleEnabled) {
            httpSecurity.authorizeRequests().antMatchers(h2ConsolePath + "/**").permitAll();
            httpSecurity.csrf().disable();
            httpSecurity.headers().frameOptions().disable();
        }
//        http    .authorizeRequests()
//                    .antMatchers("/blogEntry/edit/**","/blogEntry/edit/process","/blogEntry/delete/**")
//                    .access("hasAnyRole('AUTHENTICATED_USER', 'ROLE_ADMIN')");


        httpSecurity    .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll();

        httpSecurity    .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

        httpSecurity    .logout();





//        http.authorizeRequests().and().formLogin()//
//                // Submit URL of login page.
//                .loginProcessingUrl("/j_spring_security_check") // Submit URL
//                .loginPage("/login")//
//                .defaultSuccessUrl("/userAccountInfo")//
//                .failureUrl("/login?error=true")//
//                .usernameParameter("username")//
//                .passwordParameter("password")
//                // Config for Logout Page
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

    }
}
