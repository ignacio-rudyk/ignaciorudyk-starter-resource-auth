package com.ignaciorudyk.resource.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Optional;

@ConfigurationProperties(prefix = "ignaciorudyk.resource.authorization")
public record StarterResourceAuthProperties(
        String secretKey,
        List<CustomRole> customRoles,
        List<String> allowedOrigins,
        List<String> allowedMethods,
        List<String> allowedHeaders,
        List<String> exposedHeaders
) {

    public void addCustomRole(CustomRole newCustomRole) {
        Optional<CustomRole> existingRole = customRoles.stream()
                .filter(role -> role.roleName().equalsIgnoreCase(newCustomRole.roleName()))
                .findFirst();
        if (existingRole.isPresent())
            existingRole.get().endpoints().addAll(newCustomRole.endpoints());
        else
            customRoles.add(newCustomRole);
    }

}
