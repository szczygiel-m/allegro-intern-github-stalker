package com.szczygiel.githubstalker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.github")
public class GitHubConfig {

    private String authToken;
    private String urlPrefix;

    public GitHubConfig() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}
