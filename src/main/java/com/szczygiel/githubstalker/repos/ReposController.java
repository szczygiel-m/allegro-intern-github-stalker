package com.szczygiel.githubstalker.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/repos")
public class ReposController {

    private final ReposService reposService;

    @Autowired
    public ReposController(ReposService reposService) {
        this.reposService = reposService;
    }

    @GetMapping("{user}")
    public ResponseEntity<?> getRepositoriesByUser(
            @PathVariable String user,
            @RequestParam Optional<Long> page,
            @RequestParam Optional<Long> page_size) {

        List<RepoDto> userRepositories = reposService.getRepositoriesByUser(user, page, page_size);
        return new ResponseEntity<>(userRepositories, HttpStatus.OK);

    }
}
