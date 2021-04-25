package com.szczygiel.githubstalker.stargazers;

import com.szczygiel.githubstalker.exception.RequestTimeoutException;
import com.szczygiel.githubstalker.exception.UserNotFoundException;
import com.szczygiel.githubstalker.repos.Repo;
import com.szczygiel.githubstalker.util.ValidationUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class StargazersService {
    private final RestTemplate restTemplate;
    private static final int pageSize = 100;

    public StargazersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("StargazersByUser")
    public StargazersResponse getStargazersByUser(String user) {
        ValidationUtil.validateUsername(user);
        return new StargazersResponse(user, calculateTotalStargazers(user));
    }

    private Long calculateTotalStargazers(String user) {
        Long stargazersCounter = 0L;
        int page = 1;
        Repo[] userRepos;
        do {
            String urlEnd = createUrlEnd(user, page);
            userRepos = getReposPage(urlEnd, user);
            if (userRepos == null) {
                return 0L;
            }
            stargazersCounter += calculatePageStargazers(userRepos);
            page++;
        } while (userRepos.length == pageSize);

        return stargazersCounter;
    }

    private Long calculatePageStargazers(Repo[] userRepos) {
        Long pageStargazers = 0L;
        for (Repo repo : userRepos) {
            pageStargazers += repo.getStargazers();
        }
        return pageStargazers;
    }

    private Repo[] getReposPage(String urlEnd, String user) {
        try {
            return restTemplate
                    .exchange(urlEnd, HttpMethod.GET, null, Repo[].class).getBody();
        } catch (ResourceAccessException e) {
            throw new RequestTimeoutException("GitHub wasn't responding for too long.");
        } catch (RestClientException e) {
            throw new UserNotFoundException("User '" + user + "' not found.");
        }
    }

    private String createUrlEnd(String user, int page) {
        return user + "/repos?page=" + page + "&per_page=" + pageSize;
    }
}
