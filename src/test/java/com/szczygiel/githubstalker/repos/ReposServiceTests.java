package com.szczygiel.githubstalker.repos;

import com.szczygiel.githubstalker.exception.InvalidUsernameException;
import com.szczygiel.githubstalker.exception.UserNotFoundException;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReposServiceTests {

    @Autowired
    private ReposService reposService;

    @Test
    public void shouldReturnThirtyRepos() {
        //given
        Pair<ReposResponse, List<String>> serviceResponse;

        //when
        serviceResponse = reposService.getRepositoriesByUser("allegro", 1, 30);

        //then
        assertEquals(30, serviceResponse.getValue0().getUserRepos().size());
        assertFalse(serviceResponse.getValue1().isEmpty());
    }

    @Test
    public void shouldReturnFiftyRepos() {
        //given
        Pair<ReposResponse, List<String>> serviceResponse;

        //when
        serviceResponse = reposService.getRepositoriesByUser("allegro", 1, 50);

        //then
        assertEquals(50, serviceResponse.getValue0().getUserRepos().size());
        assertFalse(serviceResponse.getValue1().isEmpty());
    }

    @Test
    public void shouldReturnSeventyReposAtThirdPage() {
        //given
        Pair<ReposResponse, List<String>> serviceResponse;

        //when
        serviceResponse = reposService.getRepositoriesByUser("allegro", 1, 70);

        //then
        assertEquals(70, serviceResponse.getValue0().getUserRepos().size());
        assertFalse(serviceResponse.getValue1().isEmpty());
    }

    @Test
    public void shouldThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> reposService.getRepositoriesByUser("asdasdasdassfffdgasdasd", 1, 30));
    }

    @Test
    public void shouldThrowInvalidUsernameException() {
        assertThrows(InvalidUsernameException.class, () -> reposService.getRepositoriesByUser("a".repeat(50), 1, 30));
    }
}
