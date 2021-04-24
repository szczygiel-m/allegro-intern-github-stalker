package com.szczygiel.githubstalker.stargazers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class StargazersControllerTests {

    private static final String stargazersControllerPath = "/api/v1/stargazers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnMoreThanOneHundredThousandStars() throws Exception {
        //given
        String user = "torvalds";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(stargazersControllerPath + "/" + user))
                .andReturn()
                .getResponse();
        StargazersDto stargazersDto = objectMapper.readValue(response.getContentAsByteArray(), StargazersDto.class);

        //then
        assertEquals(user, stargazersDto.getUser());
        assertTrue(stargazersDto.getTotalStargazers() > 100000);
    }

    @Test
    public void shouldReturnLessThanOneHundredStars() throws Exception {
        //given
        String user = "szczygiel2000";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(stargazersControllerPath + "/" + user))
                .andReturn()
                .getResponse();
        StargazersDto stargazersDto = objectMapper.readValue(response.getContentAsByteArray(), StargazersDto.class);

        //then
        assertEquals(user, stargazersDto.getUser());
        assertTrue(stargazersDto.getTotalStargazers() < 100);
    }

    @Test
    public void shouldThrowUserNotFoundException() throws Exception {
        //given
        String invalidUsername = "aaSFASDGFKJNCSDasdnkj";

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(stargazersControllerPath + "/" + invalidUsername))
                .andReturn()
                .getResponse();

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void shouldThrowInvalidUsernameException() throws Exception {
        //given
        String invalidUsername = "a".repeat(50);

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get(stargazersControllerPath + "/" + invalidUsername))
                .andReturn()
                .getResponse();

        //then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
