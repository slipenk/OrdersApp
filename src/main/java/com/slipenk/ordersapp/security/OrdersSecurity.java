package com.slipenk.ordersapp.security;

import com.slipenk.ordersapp.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_ORDER_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_PRODUCTS_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ERROR_WITH_CONFIG_SECURITY;
import static com.slipenk.ordersapp.dictionary.Dictionary.GET_PRODUCTS_FULL_PATH;

@Configuration
public class OrdersSecurity {

    private final Logger logger = Logger.getLogger(OrdersSecurity.class.getName());

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                {
                    try {
                        configurer
                                .antMatchers(HttpMethod.POST, ADD_PRODUCTS_FULL_PATH).hasRole(Role.MANAGER.name())
                                .antMatchers(HttpMethod.GET, GET_PRODUCTS_FULL_PATH).hasRole(Role.CLIENT.name())
                                .antMatchers(HttpMethod.POST, ADD_ORDER_FULL_PATH).hasRole(Role.CLIENT.name())
                                .and()
                                .httpBasic();
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, ERROR_WITH_CONFIG_SECURITY);
                    }
                }
        );

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
