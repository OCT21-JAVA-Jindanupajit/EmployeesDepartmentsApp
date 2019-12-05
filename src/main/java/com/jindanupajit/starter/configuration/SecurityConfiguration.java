package com.jindanupajit.starter.configuration;

import com.jindanupajit.starter.service.PasswordEncoder;
import com.jindanupajit.starter.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${spring.h2.console.enabled}")
    private boolean h2ConsoleEnabled;

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


        auth    .userDetailsService(userDetailsService)
                .passwordEncoder(PasswordEncoder.getInstance());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .authorizeRequests()
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
            http.authorizeRequests().antMatchers(h2ConsolePath + "/**").permitAll();
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }
//        http    .authorizeRequests()
//                    .antMatchers("/blogEntry/edit/**","/blogEntry/edit/process","/blogEntry/delete/**")
//                    .access("hasAnyRole('AUTHENTICATED_USER', 'ROLE_ADMIN')");


        http    .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll();

        http    .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

        http    .logout();





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
