package com.andriikravchenkoo.carsaleproject.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/authentication/**")
                .permitAll()
                .antMatchers("/dealership/create", "/dealership/add-seller","/announcement/create")
                .hasAuthority("SELLER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/authentication/login").permitAll()
                .defaultSuccessUrl("/vehicle/home")
                .and()
                .logout()
                .logoutUrl("/authentication/logout")
                .logoutSuccessUrl("/authentication/login")
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
