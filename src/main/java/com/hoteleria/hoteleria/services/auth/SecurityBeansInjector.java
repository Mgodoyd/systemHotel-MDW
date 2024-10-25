package com.hoteleria.hoteleria.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hoteleria.hoteleria.interfaces.clienteInterface;
import com.hoteleria.hoteleria.interfaces.personalInterface;

/* validation of user login credentials */
@Component
@Configuration
public class SecurityBeansInjector {

    @Autowired
    private personalInterface userRepository;

    @Autowired
    private clienteInterface clientRepository;

    /**
     * Bean definition for the AuthenticationManager.
     *
     * @param authenticationConfiguration the configuration object for
     *                                    authentication
     * @return the AuthenticationManager instance
     * @throws Exception if an error occurs while retrieving the
     *                   AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates and configures an AuthenticationProvider bean.
     * 
     * This method sets up a DaoAuthenticationProvider with a custom
     * UserDetailsService
     * and PasswordEncoder. The AuthenticationProvider is responsible for
     * authenticating
     * users based on the provided credentials.
     * 
     * @return an instance of AuthenticationProvider configured with a
     *         UserDetailsService
     *         and PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    /**
     * Creates and returns a BCryptPasswordEncoder bean.
     * 
     * @return a PasswordEncoder that uses the BCrypt hashing function.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * Defines a bean for UserDetailsService that loads user-specific data.
     * 
     * @return a UserDetailsService implementation that retrieves user details by
     *         email.
     * @throws RuntimeException if the user is not found.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            return userRepository.findByEmail(email)
                    .map(user -> (UserDetails) user)
                    .orElseGet(() -> clientRepository.findByEmail(email)
                            .map(user -> (UserDetails) user)
                            .orElseThrow(() -> new RuntimeException("User not found: " + email)));
        };
    }

}