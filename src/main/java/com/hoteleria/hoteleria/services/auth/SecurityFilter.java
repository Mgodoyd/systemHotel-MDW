package com.hoteleria.hoteleria.services.auth;

import com.hoteleria.hoteleria.models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
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

        /**
         * Configures the security filter chain for the application.
         * 
         * This method sets up the security configuration using Spring Security's
         * {@link HttpSecurity} object. It disables CSRF protection, sets the session
         * management policy to stateless, and configures the authentication provider
         * and JWT authentication filter. It also defines authorization rules for
         * various endpoints by calling specific configuration methods for each type
         * of endpoint.
         * 
         * @param http the {@link HttpSecurity} to modify
         * @return the configured {@link SecurityFilterChain}
         * @throws Exception if an error occurs while configuring the security filter
         *                   chain
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(auth -> {
                                        configureAuthForPublicEndpoints(auth);
                                        configureAuthForPersonals(auth);
                                        configureAuthForHotels(auth);
                                        configureAuthForPuestos(auth);
                                        configureAuthForHabitaciones(auth);
                                        configureAuthForClientes(auth);
                                        configureAuthForReservaciones(auth);
                                        configureAuthForFacturas(auth);
                                        configureAuthForServicios(auth);
                                        configureAuthForParqueos(auth);
                                        configureAuthForServicioHabitaciones(auth);
                                        configureAuthForUsoInstalaciones(auth);
                                        configureAuthForPromociones(auth);
                                        configureAuthForDescuentos(auth);
                                        auth.anyRequest().denyAll();
                                });

                return http.build();
        }

        private void configureAuthForPublicEndpoints(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/v1/register").permitAll();
                auth.requestMatchers(HttpMethod.PUT, "/api/v1/changePassword").permitAll();
        }

        private void configureAuthForPersonals(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/personals")
                                .hasAuthority(Permission.READ_ALL_USERS.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/personals/email", "/api/v1/personals/uuid",
                                "/api/v1/personals/phone")
                                .hasAuthority(Permission.READ_ONE_HUESPED.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/personals")
                                .hasAuthority(Permission.SAVE_ONE_USER.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/personals")
                                .hasAuthority(Permission.UPDATE_ONE_USER.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/personals")
                                .hasAuthority(Permission.DELETE_ONE_USER.name());
        }

        private void configureAuthForHotels(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/hotels")
                                .hasAuthority(Permission.READ_HOTEL.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/hotels/name", "/api/v1/hotels/uuid")
                                .hasAuthority(Permission.READ_ONE_HOTEL.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/hotels")
                                .hasAuthority(Permission.SAVE_ONE_HOTEL.name());
                auth.requestMatchers(HttpMethod.PUT, "/api/v1/hotels")
                                .hasAuthority(Permission.UPDATE_ONE_HOTEL.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/hotels")
                                .hasAuthority(Permission.DELETE_ONE_HOTEL.name());
        }

        private void configureAuthForPuestos(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/puestos")
                                .hasAuthority(Permission.READ_PUESTOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/puestos/name", "/api/v1/puestos/uuid")
                                .hasAuthority(Permission.READ_ONE_PUESTO.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/puestos")
                                .hasAuthority(Permission.SAVE_ONE_PUESTO.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/puestos")
                                .hasAuthority(Permission.UPDATE_ONE_PUESTO.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/puestos")
                                .hasAuthority(Permission.DELETE_ONE_PUESTO.name());
        }

        private void configureAuthForHabitaciones(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/habitaciones").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/v1/habitaciones/number", "/api/v1/habitaciones/uuid")
                                .permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/v1/habitaciones")
                                .hasAuthority(Permission.SAVE_ONE_HABITACION.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/habitaciones")
                                .hasAuthority(Permission.UPDATE_ONE_HABITACION.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/habitaciones")
                                .hasAuthority(Permission.DELETE_ONE_HABITACION.name());
        }

        private void configureAuthForClientes(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/clientes")
                                .hasAuthority(Permission.READ_ALL_USERS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/clientes/nit", "/api/v1/clientes/uuid")
                                .hasAuthority(Permission.READ_ONE_USER.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll();
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/clientes")
                                .hasAuthority(Permission.UPDATE_ONE_USER.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/clientes")
                                .hasAuthority(Permission.DELETE_ONE_USER.name());
        }

        private void configureAuthForReservaciones(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/reservaciones")
                                .hasAuthority(Permission.READ_ALL_RESERVACIONES.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/reservaciones/id")
                                .hasAuthority(Permission.READ_ONE_RESERVACION.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/reservaciones")
                                .hasAuthority(Permission.SAVE_ONE_RESERVACION.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/reservaciones")
                                .hasAuthority(Permission.UPDATE_ONE_RESERVACION.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/reservaciones")
                                .hasAuthority(Permission.DELETE_ONE_RESERVACION.name());
        }

        private void configureAuthForFacturas(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/facturas")
                                .hasAuthority(Permission.READ_ALL_FACTURAS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/facturas/id")
                                .hasAuthority(Permission.READ_ONE_FACTURA.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/facturas")
                                .hasAuthority(Permission.SAVE_ONE_FACTURA.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/facturas")
                                .hasAuthority(Permission.UPDATE_ONE_FACTURA.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/facturas")
                                .hasAuthority(Permission.DELETE_ONE_FACTURA.name());
        }

        private void configureAuthForServicios(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/servicios")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/servicios/id", "/api/v1/servicios/nombre")
                                .hasAuthority(Permission.READ_ONE_SERVICIO.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/servicios")
                                .hasAuthority(Permission.SAVE_ONE_SERVICIO.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/servicios")
                                .hasAuthority(Permission.UPDATE_ONE_SERVICIO.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/servicios")
                                .hasAuthority(Permission.DELETE_ONE_SERVICIO.name());
        }

        private void configureAuthForParqueos(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/parqueos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/parqueos/id")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/parqueos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/parqueos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/parqueos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
        }

        private void configureAuthForServicioHabitaciones(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/servicioHabitaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/servicioHabitaciones/id")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/servicioHabitaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/servicioHabitaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/servicioHabitaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
        }

        private void configureAuthForUsoInstalaciones(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/usoInstalaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/usoInstalaciones/id")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/usoInstalaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/usoInstalaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/usoInstalaciones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
        }

        private void configureAuthForPromociones(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/promociones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/promociones/id", "/api/v1/promociones/uuid")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/promociones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/promociones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/promociones")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
        }

        private void configureAuthForDescuentos(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                auth.requestMatchers(HttpMethod.GET, "/api/v1/descuentos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.GET, "/api/v1/descuentos/id", "/api/v1/descuentos/uuid")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.POST, "/api/v1/descuentos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.PATCH, "/api/v1/descuentos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
                auth.requestMatchers(HttpMethod.DELETE, "/api/v1/descuentos")
                                .hasAuthority(Permission.READ_ALL_SERVICIOS.name());
        }
}
