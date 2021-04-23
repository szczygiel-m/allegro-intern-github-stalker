package com.szczygiel.githubstalker.stargazers;

import com.szczygiel.githubstalker.config.GitHubConfig;
import com.szczygiel.githubstalker.exception.RequestTimeoutException;
import com.szczygiel.githubstalker.repos.RepoDto;
import com.szczygiel.githubstalker.util.ValidationUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class StargazersService {

    private final GitHubConfig gitHubConfig;
    private final RestTemplate restTemplate;

    public StargazersService(GitHubConfig gitHubConfig, RestTemplate restTemplate) {
        this.gitHubConfig = gitHubConfig;
        this.restTemplate = restTemplate;
    }

    @Cacheable("StargazersByUser")
    public StargazersDto getStargazersByUser(String user) {
        ValidationUtil.validateUsername(user);
        return new StargazersDto(user, calculateTotalStargazers(user));
    }

    private Long calculateTotalStargazers(String user) {
        Long totalStargazers = 0L;
        int pageSize = 100;
        int page = 1;
        RepoDto[] userRepos;
        HttpEntity<?> entity = createHttpEntity();
        do {
            String url = gitHubConfig.getUrlPrefix() + user + "/repos?page=" + page + "&per_page=" + pageSize;
            try {
                ResponseEntity<RepoDto[]> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, RepoDto[].class);
                userRepos = exchange.getBody();
                assert userRepos != null;
                for (RepoDto repo : userRepos) {
                    totalStargazers += repo.getStargazers();
                }
            } catch (RestClientException e){
                throw new RequestTimeoutException();
            }
            page++;
        } while (userRepos.length == pageSize);

        return totalStargazers;
    }

    private HttpEntity<?> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + gitHubConfig.getAuthToken());
        return new HttpEntity<>(null, headers);
    }
}
