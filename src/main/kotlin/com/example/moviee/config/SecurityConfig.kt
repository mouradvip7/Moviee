package com.example.moviee.config

import com.example.moviee.service.UserService
import com.example.moviee.utils.MovieReactiveUserDetailsService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http : ServerHttpSecurity) : SecurityWebFilterChain {
        http.authorizeExchange()
            .pathMatchers("/movies/**")
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .disable();

        return http.build();
    }

    @Bean
    fun authenticationManager(movieeReactiveUserDetailsService: MovieReactiveUserDetailsService): UserDetailsRepositoryReactiveAuthenticationManager {
        val userDetailsRepositoryReactiveAuthenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(movieeReactiveUserDetailsService)
        userDetailsRepositoryReactiveAuthenticationManager.setPasswordEncoder(passwordEncoder())

        return userDetailsRepositoryReactiveAuthenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Profile("default")
    fun applicationRunner(userService : UserService): ApplicationRunner {
        return ApplicationRunner {
            userService.save(com.example.moviee.model.User(1, "user", passwordEncoder().encode("password"), "USER", "User of Moviee")).subscribe();
            userService.save(com.example.moviee.model.User(2, "admin", passwordEncoder().encode("password"), "ADMIN", "Admin of Moviee")).subscribe()
        }
    }

}