package com.company.charging.api.security;

import com.company.charging.api.model.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Author: ASOU SAFARI
 * Date:9/17/24
 * Time:12:57 PM
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
 //       CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        httpSecurity
                .securityContext((context -> context.requireExplicitSave(false)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

//                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
//                        .ignoringRequestMatchers("/api/v1/create")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                //disable in development
                .csrf(AbstractHttpConfigurer::disable)

  //              .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)


                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/users/**")
                                    .hasAnyRole(RoleType.MANAGER.name(),RoleType.ADMIN.name())
                                .requestMatchers("/api/v1/transactions/**")
                                    .hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers("/api/v1/plans/**")
                                    .hasAnyRole(RoleType.MANAGER.name())
                                .requestMatchers("/api/v1/paymentUsers/create")
                                    .hasRole(RoleType.USER.name())
                                .requestMatchers("/api/v1/create").permitAll())

                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
