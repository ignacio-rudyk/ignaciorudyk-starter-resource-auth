package com.ignaciorudyk.resource.auth.config.security;

import com.ignaciorudyk.resource.auth.config.CustomRole;
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

import java.util.Arrays;

/**
 * SecurityConfig simplificado: sin UserDetailsService ni AuthenticationManager
 * porque este servicio no autentica — solo verifica el JWT que viene del auth-service.
 */
@AutoConfiguration(after = SecurityAutoConfiguration.class)
public class SecurityFilterChainAutoConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final StarterResourceAuthProperties starterAuthenticationProperties;

    public SecurityFilterChainAutoConfiguration(JwtAuthenticationFilter jwtAuthFilter,
                                                AuthenticationEntryPoint authenticationEntryPoint,
                                                AccessDeniedHandler accessDeniedHandler,
                                                StarterResourceAuthProperties starterAuthenticationProperties) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.starterAuthenticationProperties = starterAuthenticationProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectProvider<SecurityCustomizer> customizer) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        setCustomAuthorizeHttpRequest(http);
        setCustomizer(http, customizer);
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(starterAuthenticationProperties.allowedOrigins());
        config.setAllowedMethods(starterAuthenticationProperties.allowedMethods());
        config.setAllowedHeaders(starterAuthenticationProperties.allowedHeaders());
        config.setExposedHeaders(starterAuthenticationProperties.exposedHeaders());
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private void setCustomAuthorizeHttpRequest(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> starterAuthenticationProperties.customRoles()
                .forEach(e -> {
                    if("PERMIT_ALL".equalsIgnoreCase(e.roleName()))
                        auth.requestMatchers(e.endpoints().toArray(String[]::new)).permitAll();
                    else
                        auth.requestMatchers(e.endpoints().toArray(String[]::new)).hasAuthority(e.roleName().toUpperCase());
                })
        );
    }

    private static void setCustomizer(HttpSecurity http, ObjectProvider<SecurityCustomizer> customizer) {
        customizer.ifAvailable(c -> {
            try { c.customize(http); }
            catch (Exception e) { throw new RuntimeException(e); }
        });
    }

}