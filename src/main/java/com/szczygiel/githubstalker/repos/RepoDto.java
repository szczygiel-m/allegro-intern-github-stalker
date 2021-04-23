package com.szczygiel.githubstalker.repos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepoDto {

    private String name;

    @JsonProperty("stargazers_count")
    private Long stargazers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStargazers() {
        return stargazers;
    }

    public void setStargazers(Long stargazers) {
        this.stargazers = stargazers;
    }
}
