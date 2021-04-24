package com.szczygiel.githubstalker.repos;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/repos")
public class ReposController {

    private final ReposService reposService;

    @Autowired
    public ReposController(ReposService reposService) {
        this.reposService = reposService;
    }

    /*
        GET /api/v1/repos/{user}
        @variable user name to list repositories
        @params pagination params
        @return paginated list of user repos
     */
    @GetMapping("{user}")
    public ResponseEntity<?> getRepositoriesByUser(
            @PathVariable String user,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "30") int per_page) {
        Pair<ReposResponseDto, List<String>> serverResponse = reposService.getRepositoriesByUser(user, page, per_page);
        if (serverResponse.getValue1() != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Link", serverResponse.getValue1().stream().map(String::valueOf).collect(Collectors.joining("")));
            return new ResponseEntity<>(serverResponse.getValue0(), responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(serverResponse.getValue0(), HttpStatus.OK);

    }
}
