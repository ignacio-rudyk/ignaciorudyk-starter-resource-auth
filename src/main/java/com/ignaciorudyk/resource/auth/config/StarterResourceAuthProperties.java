package com.ignaciorudyk.resource.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "ignaciorudyk.resource.authorization")
@Data
public class StarterResourceAuthProperties {

    private String secretKey;

    private List<String> publicEndpoints = new ArrayList<>();
    private List<String> userEndpoints = new ArrayList<>();
    private List<String> adminEndpoints = new ArrayList<>();

    private List<String> allowedOrigins = new ArrayList<>();
    private List<String> allowedMethods = new ArrayList<>();
    private List<String> allowedHeaders = new ArrayList<>();
    private List<String> exposedHeaders = new ArrayList<>();

}
