package com.slipenk.ordersapp.security;

import com.slipenk.ordersapp.entity.Role;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.logging.Level;

import static com.slipenk.ordersapp.dictionary.Dictionary.ORDERS_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PAY_ORDER_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCTS_FULL_PATH;

@Configuration
@Log
public class OrdersSecurity {

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
                                .antMatchers(HttpMethod.POST, PRODUCTS_FULL_PATH).hasRole(Role.MANAGER.name())
                                .antMatchers(HttpMethod.GET, PRODUCTS_FULL_PATH).hasRole(Role.CLIENT.name())
                                .antMatchers(HttpMethod.POST, ORDERS_FULL_PATH).hasRole(Role.CLIENT.name())
                                .antMatchers(HttpMethod.POST, PAY_ORDER_FULL_PATH).hasRole(Role.CLIENT.name())
                                .and()
                                .httpBasic();
                    } catch (Exception e) {
                        log.log(Level.SEVERE, e.getMessage());
                    }
                }
        );

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
