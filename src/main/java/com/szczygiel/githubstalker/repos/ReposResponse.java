package com.szczygiel.githubstalker.repos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReposResponse {

    private final String user;

    @JsonProperty("user_repos")
    private final List<Repo> userRepos;

    public ReposResponse(String user, List<Repo> userRepos) {
        this.user = user;
        this.userRepos = userRepos;
    }

    public List<Repo> getUserRepos() {
        return userRepos;
    }

    public String getUser() {
        return user;
    }
}
