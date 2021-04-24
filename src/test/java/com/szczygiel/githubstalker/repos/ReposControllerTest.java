package com.szczygiel.githubstalker.repos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ReposControllerTest {

    private static final String reposControllerPath = "/api/v1/repos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnThirtyReposAndHeadersLink() throws Exception {
        //given
        String user = "microsoft";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(reposControllerPath + "/" + user))
                .andReturn()
                .getResponse();
        ReposResponseDto reposResponseDto = objectMapper.readValue(response.getContentAsByteArray(), ReposResponseDto.class);

        //then
        assertEquals(30, reposResponseDto.getUserRepos().size());
        assertEquals(user, reposResponseDto.getUser());
        assertNotNull(response.getHeader("Link"));
    }

    @Test
    public void shouldReturnFiftyReposAndHeadersLink() throws Exception {
        //given
        String user = "microsoft";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(reposControllerPath + "/" + user + "?per_page=50"))
                .andReturn()
                .getResponse();
        ReposResponseDto reposResponseDto = objectMapper.readValue(response.getContentAsByteArray(), ReposResponseDto.class);

        //then
        assertEquals(50, reposResponseDto.getUserRepos().size());
        assertEquals(user, reposResponseDto.getUser());
        assertNotNull(response.getHeader("Link"));
    }

    @Test
    public void shouldReturnSeventyReposAtThirdPageAndHeadersLink() throws Exception {
        //given
        String user = "microsoft";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(reposControllerPath + "/" + user + "?page=3&per_page=70"))
                .andReturn()
                .getResponse();
        ReposResponseDto reposResponseDto = objectMapper.readValue(response.getContentAsByteArray(), ReposResponseDto.class);

        //then
        assertEquals(70, reposResponseDto.getUserRepos().size());
        assertEquals(user, reposResponseDto.getUser());
        assertNotNull(response.getHeader("Link"));
    }

    @Test
    public void shouldThrowUserNotFoundException() throws Exception {
        //given
        String invalidUsername = "aaSFASDGFKJNCSDasdnkj";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(reposControllerPath + "/" + invalidUsername))
                .andReturn()
                .getResponse();

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertNull(response.getHeader("Link"));
    }

    @Test
    public void shouldThrowInvalidUsernameException() throws Exception {
        //given
        String invalidUsername = "a".repeat(50);

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(reposControllerPath + "/" + invalidUsername))
                .andReturn()
                .getResponse();

        //then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertNull(response.getHeader("Link"));
    }
}
