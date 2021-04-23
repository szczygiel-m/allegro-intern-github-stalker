package com.szczygiel.githubstalker.repos;

import com.szczygiel.githubstalker.config.GitHubConfig;
import com.szczygiel.githubstalker.util.ValidationUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReposService {

    private final GitHubConfig gitHubConfig;
    private final RestTemplate restTemplate;

    public ReposService(GitHubConfig gitHubConfig, RestTemplate restTemplate) {
        this.gitHubConfig = gitHubConfig;
        this.restTemplate = restTemplate;
    }

    @Cacheable("RepositoriesByUser")
    public List<RepoDto> getRepositoriesByUser(String user,
                                               Optional<Long> page,
                                               Optional<Long> pageSize) {
        ValidationUtil.validateUsername(user);
        HttpEntity<?> entity = createHttpEntity();
        String url = gitHubConfig.getUrlPrefix() + user + "/repos?page=" + page.orElse(1L) + "&per_page=" + pageSize.orElse(30L);
        ResponseEntity<RepoDto[]> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, RepoDto[].class);
        RepoDto[] userRepos = exchange.getBody();
        assert userRepos != null;
        return Arrays.stream(userRepos).collect(Collectors.toList());
    }

    private HttpEntity<?> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + gitHubConfig.getAuthToken());
        return new HttpEntity<>(null, headers);
    }
}
