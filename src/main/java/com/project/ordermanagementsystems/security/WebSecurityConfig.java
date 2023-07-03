package com.project.ordermanagementsystems.security;

import com.project.ordermanagementsystems.security.jwt.AuthEntryPointJwt;
import com.project.ordermanagementsystems.security.jwt.AuthTokenFilter;
import com.project.ordermanagementsystems.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @return
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**", "/login/**", "/oauth2/**")
                .permitAll()
                .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**")
                .permitAll()
                .antMatchers("/orders/user")
                .fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/orders/**")
                .fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/orders/**")
                .fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/products/**")
                .permitAll()
                .anyRequest()
                .hasRole(Constants.ADMIN)
        ;
        http.authenticationProvider(securityUtil.authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
