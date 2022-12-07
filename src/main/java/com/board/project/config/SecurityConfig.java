package com.board.project.config;

import com.board.project.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

/*@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/js/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                    .antMatchers("/", "/login/**", "/resources/**", "/css/**", "/js/**")
                    .permitAll()
                .and()
                    .formLogin()
                    .usernameParameter("userId")
                    .passwordParameter("userPw")
                    .successHandler(loginSuccessHandler())
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/board/boardList")
                    .invalidateHttpSession(true)
                .and()
                    .exceptionHandling().accessDeniedPage("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/", "/login/**", "/resources/**", "/css/**", "/js/**")
                .permitAll()
            .and()
                .formLogin()
                .loginPage("/member/loginForm")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(loginSuccessHandler())
                .loginProcessingUrl("/login")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/board/boardList")
                .invalidateHttpSession(true)
            .and()
                .exceptionHandling().accessDeniedPage("/");

        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/resources/**", "/js/**", "/css/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
