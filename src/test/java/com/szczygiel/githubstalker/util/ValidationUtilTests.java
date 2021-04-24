package com.szczygiel.githubstalker.util;

import com.szczygiel.githubstalker.exception.InvalidUsernameException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ValidationUtilTests {

    @Test
    public void emptyUsername_shouldThrowInvalidUsernameException() {
        //given
        String emptyUsername = "";
        String expectedExceptionMassage = "Username must be between 1 and 39 characters. Your is " + 0 + ".";

        //when
        Exception exception = assertThrows(InvalidUsernameException.class,
                () -> ValidationUtil.validateUsername(emptyUsername));

        //then
        assertEquals(expectedExceptionMassage, exception.getMessage());
    }

    @Test
    public void tooLongUsername_shouldThrowInvalidUsernameException() {
        //given
        String tooLongUsername = "a".repeat(50);
        String expectedExceptionMassage = "Username must be between 1 and 39 characters. Your is " + tooLongUsername.length() + ".";

        //when
        Exception exception = assertThrows(InvalidUsernameException.class,
                () -> ValidationUtil.validateUsername(tooLongUsername));

        //then
        assertEquals(expectedExceptionMassage, exception.getMessage());
    }

    @Test
    public void shouldNotThrowInvalidUsernameException() {
        //given
        String validUsername = "szczygiel2000";

        //then
        assertDoesNotThrow(() -> ValidationUtil.validateUsername(validUsername));
    }
}
