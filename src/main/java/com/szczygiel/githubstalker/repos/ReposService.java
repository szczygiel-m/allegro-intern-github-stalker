package com.szczygiel.githubstalker.repos;

import com.szczygiel.githubstalker.exception.RequestTimeoutException;
import com.szczygiel.githubstalker.exception.UserNotFoundException;
import com.szczygiel.githubstalker.util.UrlGeneratorUtil;
import com.szczygiel.githubstalker.util.ValidationUtil;
import org.javatuples.Pair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReposService {

    private final RestTemplate restTemplate;

    public ReposService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("RepositoriesByUser")
    public Pair<ReposResponse, List<String>> getRepositoriesByUser(String user,
                                                                   int page,
                                                                   int pageSize) {
        ValidationUtil.validateUsername(user);
        String urlEnd = UrlGeneratorUtil.createUrlEnd(user, page, pageSize);
        ResponseEntity<List<Repo>> responseEntity = sendGitHubRequest(urlEnd, user);
        List<String> paginationLinks = responseEntity.getHeaders().get("Link");
        if (paginationLinks != null) {
            paginationLinks = UrlGeneratorUtil.generatePaginationLinks(responseEntity.getHeaders());
        }
        return new Pair<>(new ReposResponse(user, responseEntity.getBody()), paginationLinks);
    }

    private ResponseEntity<List<Repo>> sendGitHubRequest(String urlEnd, String user) {
        try {
            return restTemplate.exchange(urlEnd, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        } catch (ResourceAccessException e) {
            throw new RequestTimeoutException("GitHub wasn't responding for too long.");
        } catch (RestClientException e) {
            throw new UserNotFoundException("User '" + user + "' not found.");
        }
    }
}
