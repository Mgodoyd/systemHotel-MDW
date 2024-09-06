package com.hoteleria.hoteleria.services.auth;

import com.hoteleria.hoteleria.models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(
                        sessionMangConfig -> sessionMangConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/register").permitAll();
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/v1/changePassword")
                            .permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/pets/notoken/uuid")
                            .permitAll();
                    // authConfig.requestMatchers(HttpMethod.GET, "user").hasAnyAuthority(
                    // Permission.READ_ALL_RAZAS.name(),
                    // Permission.EMAIL.name(),
                    // Permission.ISS.name());

                    // authConfig.requestMatchers(HttpMethod.GET, "/user").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/personals")
                            .hasAuthority(Permission.READ_ALL_USERS.name());
                    // authConfig.requestMatchers(HttpMethod.POST, "/api/v1/users/username")
                    // .hasAuthority(Permission.READ_ONE_USER.name());
                    // authConfig.requestMatchers(HttpMethod.POST, "/api/v1/users/uuid")
                    // .hasAuthority(Permission.READ_ONE_USER.name());
                    // authConfig.requestMatchers(HttpMethod.PUT, "/api/v1/users")
                    // .hasAuthority(Permission.UPDATE_ONE_USER.name());
                    // authConfig.requestMatchers(HttpMethod.DELETE, "/api/v1/users")
                    // .hasAuthority(Permission.DELETE_ONE_USER.name());
                    // authConfig.requestMatchers(HttpMethod.GET, "/api/v1/razas")
                    // .hasAnyAuthority(
                    // Permission.READ_ALL_RAZAS.name(),
                    // Permission.EMAIL.name(),
                    // Permission.ISS.name());

                    authConfig.anyRequest().denyAll();

                });

        return http.build();

    }

}