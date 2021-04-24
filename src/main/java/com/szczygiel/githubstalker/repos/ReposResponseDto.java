package com.szczygiel.githubstalker.repos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReposResponseDto {

    private final String user;

    @JsonProperty("user_repos")
    private final List<RepoDto> userRepos;

    public ReposResponseDto(String user, List<RepoDto> userRepos) {
        this.user = user;
        this.userRepos = userRepos;
    }

    public List<RepoDto> getUserRepos() {
        return userRepos;
    }

    public String getUser() {
        return user;
    }
}
