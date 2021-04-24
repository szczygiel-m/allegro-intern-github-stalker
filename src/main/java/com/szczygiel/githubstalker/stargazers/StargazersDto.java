package com.szczygiel.githubstalker.stargazers;

public class StargazersDto {

    private final String user;
    private final Long totalStargazers;

    public StargazersDto(String username, Long totalStargazers) {
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
