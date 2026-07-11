package com.ignaciorudyk.resource.auth.config.security;

import com.ignaciorudyk.resource.auth.config.JwtAuthenticationFilter;
import com.ignaciorudyk.resource.auth.config.StarterResourceAuthProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * SecurityConfig simplificado: sin UserDetailsService ni AuthenticationManager
 * porque este servicio no autentica — solo verifica el JWT que viene del auth-service.
 */
@AutoConfiguration(after = SecurityAutoConfiguration.class)
public class SecurityFilterChainAutoConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final StarterResourceAuthProperties starterAuthentitcationProperties;

    public SecurityFilterChainAutoConfiguration(JwtAuthenticationFilter jwtAuthFilter,
                                                AuthenticationEntryPoint authenticationEntryPoint,
                                                AccessDeniedHandler accessDeniedHandler,
                                                StarterResourceAuthProperties starterAuthentitcationProperties) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.starterAuthentitcationProperties = starterAuthentitcationProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectProvider<SecurityCustomizer> customizer) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(starterAuthentitcationProperties.getPublicEndpoints().toArray(String[]::new)).permitAll()
                    .requestMatchers(starterAuthentitcationProperties.getUserEndpoints().toArray(String[]::new)).hasRole("USER")
                    .requestMatchers(starterAuthentitcationProperties.getAdminEndpoints().toArray(String[]::new)).hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        setCustomizer(http, customizer);
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(starterAuthentitcationProperties.getAllowedOrigins());
        config.setAllowedMethods(starterAuthentitcationProperties.getAllowedMethods());
        config.setAllowedHeaders(starterAuthentitcationProperties.getAllowedHeaders());
        config.setExposedHeaders(starterAuthentitcationProperties.getExposedHeaders());
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private static void setCustomizer(HttpSecurity http, ObjectProvider<SecurityCustomizer> customizer) {
        customizer.ifAvailable(c -> {
            try { c.customize(http); }
            catch (Exception e) { throw new RuntimeException(e); }
        });
    }

}