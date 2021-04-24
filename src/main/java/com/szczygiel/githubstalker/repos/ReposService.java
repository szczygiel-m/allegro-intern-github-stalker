package com.szczygiel.githubstalker.repos;

import com.szczygiel.githubstalker.exception.RequestTimeoutException;
import com.szczygiel.githubstalker.exception.UserNotFoundException;
import com.szczygiel.githubstalker.util.ValidationUtil;
import org.javatuples.Pair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReposService {

    private final RestTemplate restTemplate;

    public ReposService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("RepositoriesByUser")
    public Pair<ReposResponseDto, List<String>> getRepositoriesByUser(String user,
                                                                      Optional<Long> page,
                                                                      Optional<Long> pageSize) {
        ValidationUtil.validateUsername(user);
        String urlEnd = createUrlEnd(user, page, pageSize);
        ResponseEntity<List<RepoDto>> responseEntity = sendGitHubRequest(urlEnd);
        List<String> paginationLinks = responseEntity.getHeaders().get("Link");
        if (paginationLinks != null) {
            paginationLinks = generatePaginationLinks(responseEntity.getHeaders());
        }

        return new Pair<>(new ReposResponseDto(responseEntity.getBody()), paginationLinks);
    }

    private ResponseEntity<List<RepoDto>> sendGitHubRequest(String urlEnd) {
        try {
            return restTemplate.exchange(urlEnd, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        } catch (ResourceAccessException e) {
            throw new RequestTimeoutException("GitHub wasn't responding for too long.");
        } catch (RestClientException e) {
            throw new UserNotFoundException("User not found.");
        }
    }

    private List<String> generatePaginationLinks(HttpHeaders headers) {
        List<String> paginationLinks = headers.get("Link");
        List<String> changedLinks = new ArrayList<>();
        assert paginationLinks != null;
        for (String link : paginationLinks) {
            changedLinks.add(link.replaceAll("https://api.github.com/user/[0-9]+/repos",
                    (ServletUriComponentsBuilder.fromCurrentRequest().toUriString().replaceAll("\\?page=.", ""))));
        }
        return changedLinks;
    }

    private String createUrlEnd(String user, Optional<Long> page, Optional<Long> pageSize) {
        return user + "/repos"
                + (page.map(value -> "?page=" + value).orElse(""))
                + (pageSize.map(value -> "?per_page=" + value).orElse(""));
    }
}
