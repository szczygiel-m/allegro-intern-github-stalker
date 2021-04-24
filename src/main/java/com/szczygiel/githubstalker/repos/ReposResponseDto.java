package com.szczygiel.githubstalker.repos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReposResponseDto {

    @JsonProperty("user_repos")
    private final List<RepoDto> userRepos;

    public ReposResponseDto(List<RepoDto> userRepos) {
        this.userRepos = userRepos;
    }

    public List<RepoDto> getUserRepos() {
        return userRepos;
    }
}
