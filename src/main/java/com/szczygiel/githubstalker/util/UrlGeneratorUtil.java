package com.szczygiel.githubstalker.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class UrlGeneratorUtil {

    public static List<String> generatePaginationLinks(HttpHeaders headers) {
        List<String> paginationLinks = headers.get("Link");
        List<String> changedLinks = new ArrayList<>();
        assert paginationLinks != null;
        for (String link : paginationLinks) {
            changedLinks.add(link.replaceAll("https://api.github.com/user/[0-9]+/repos",
                    (ServletUriComponentsBuilder.fromCurrentRequest().toUriString().replaceAll("\\?page=.", ""))));
        }
        return changedLinks;
    }

    public static String createUrlEnd(String user, int page, int pageSize) {
        return user + "/repos"
                + (page == 1 && pageSize == 30 ? "" : "?")
                + (page == 1 ? "" : "page=" + page)
                + (page != 1 && pageSize != 30 ? "&" : "")
                + (pageSize == 30 ? "" : "per_page=" + pageSize);
    }
}
