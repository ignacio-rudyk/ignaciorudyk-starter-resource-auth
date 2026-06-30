package com.ignaciorudyk.resource.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "ignaciorudyk.resource.authorization")
@Data
public class StarterResourceAuthorizationProperties {

    private List<String> publicEndpoints = new ArrayList<>();
    private List<String> userEndpoints = new ArrayList<>();
    private List<String> adminEndpoints = new ArrayList<>();

}