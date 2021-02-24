package com.cybertek.config;

import com.cybertek.service.SecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityService securityService;
    private AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/**").hasAuthority("Admin")//authorization yapacaksan antMatchers kullanirsin
                .antMatchers("/project/**").hasAuthority("Manager")//HTML de sec:authentication ile belirtiyoruz
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                //.defaultSuccessUrl("/welcome")//bu satirda tum authenticatedlar welcome a gidiyordu
                .successHandler(authSuccessHandler)//bu satir bizi sayfalari ilgili userlarin gormesini saglatacak
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                .and()
                .rememberMe()//HTML de name="remember-me" seklinde olmak zorunda
                .tokenValiditySeconds(120)//Koymazsak limitsiz hatirlayacak
                .key("cyberteksecret")
                .userDetailsService(securityService);

    }
}
