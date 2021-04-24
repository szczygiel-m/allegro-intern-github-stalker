package com.szczygiel.githubstalker.util;

import com.szczygiel.githubstalker.exception.InvalidUsernameException;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    public static void validateUsername(String username) {
        if (username.length() > 39 || username.isEmpty()) {
            throw new InvalidUsernameException(
                    "Username must be between 1 and 39 characters. Your is " + username.length() + "."
            );
        }
    }
}
