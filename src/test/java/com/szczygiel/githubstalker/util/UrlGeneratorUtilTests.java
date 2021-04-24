package com.szczygiel.githubstalker.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UrlGeneratorUtilTests {

    @Test
    public void shouldGeneratePaginationLinks() {
        //given
        HttpHeaders sampleLinkHeader = new HttpHeaders();
        sampleLinkHeader.set("Link", "<https://api.github.com/user/562236/repos?page=2>; rel=\"next\", <https://api.github.com/user/562236/repos?page=3>; rel=\"last\"");
        List<String> expectedLinks = List.of(
                "<http://localhost?page=2>; rel=\"next\", <http://localhost?page=3>; rel=\"last\"");

        //when
        List<String> generatedLinks = UrlGeneratorUtil.generatePaginationLinks(sampleLinkHeader);

        //then
        assertEquals(expectedLinks, generatedLinks);
    }

    @Test
    public void shouldCreateUrlEndWithPagination() {
        //given
        String expectedUrlEnd = "allegro/repos?page=2&per_page=60";

        //when
        String actualUrlEnd = UrlGeneratorUtil.createUrlEnd("allegro", 2, 60);

        //then
        assertEquals(expectedUrlEnd, actualUrlEnd);
    }

    @Test
    public void shouldCreateUrlEnd() {
        //given
        String expectedUrlEnd = "allegro/repos";

        //when
        String actualUrlEnd = UrlGeneratorUtil.createUrlEnd("allegro", 1, 30);

        //then
        assertEquals(expectedUrlEnd, actualUrlEnd);
    }
}
