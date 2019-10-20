package org.ziniakov.musiccalendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ziniakov.musiccalendar.dto.songkick.Artist;
import org.ziniakov.musiccalendar.service.ArtistsService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/artists")
@RequiredArgsConstructor
public class ArtistsController {

    private final ArtistsService service;

    @GetMapping(path = "/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Artist> search(@RequestParam String query) {
        return service.search(query);
    }

    @GetMapping(path = "/getUserTracked", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Artist> getUserTracked(@RequestParam String username) {
        return service.getUserTracked(username);
    }

    @GetMapping(path = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void save(@RequestParam List<Artist> artists) {
        service.save(artists);
    }
}
