package org.ziniakov.musiccalendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ziniakov.musiccalendar.dto.songkick.Artist;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/artists")
@RequiredArgsConstructor
public class ArtistsController {

    @Value("${songkick.api-key}")
    String apiKey;

    SongkickGateway gateway;

    @GetMapping(path = "/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Artist> searchArtists(@RequestParam String query) {
        return gateway.searchArtists(apiKey, query).getResults().getArtists();
    }
}
