package com.szczygiel.githubstalker.stargazers;

public class StargazersResponse {

    private final String user;
    private final Long totalStargazers;

    public StargazersResponse(String username, Long totalStargazers) {
        this.user = username;
        this.totalStargazers = totalStargazers;
    }

    public String getUser() {
        return user;
    }

    public Long getTotalStargazers() {
        return totalStargazers;
    }
}
