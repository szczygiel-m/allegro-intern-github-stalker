package com.szczygiel.githubstalker.repos;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Pair<ReposResponseDto, List<String>> serverResponse = reposService.getRepositoriesByUser(user, page, page_size);
        if (serverResponse.getValue1() != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Link", serverResponse.getValue1().stream().map(String::valueOf).collect(Collectors.joining("")));
            return new ResponseEntity<>(serverResponse.getValue0(), responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(serverResponse.getValue0(), HttpStatus.OK);

    }
}
