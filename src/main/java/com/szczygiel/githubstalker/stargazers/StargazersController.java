package com.szczygiel.githubstalker.stargazers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stargazers")
public class StargazersController {

    private final StargazersService stargazersService;

    @Autowired
    public StargazersController(StargazersService stargazersService) {
        this.stargazersService = stargazersService;
    }

    @GetMapping("{user}")
    public ResponseEntity<?> getStargazersByUser(@PathVariable String user) {
            StargazersDto stargazersDto = stargazersService.getStargazersByUser(user);
            return new ResponseEntity<>(stargazersDto, HttpStatus.OK);
    }
}
