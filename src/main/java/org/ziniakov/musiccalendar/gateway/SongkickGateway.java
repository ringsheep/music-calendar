package org.ziniakov.musiccalendar.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.ziniakov.musiccalendar.dto.songkick.Response;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(
        name = "songkick-api",
        url = "${songkick.url}"
)
public interface SongkickGateway {

    String API_KEY_PARAM = "apikey";
    String QUERY_PARAM = "query";
    String PAGE_PARAM = "page";

    @GetMapping(value = "/api/3.0/search/artists.json", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Response searchArtists(
            @RequestParam(API_KEY_PARAM) String apiKey,
            @RequestParam(PAGE_PARAM) int page,
            @RequestParam(QUERY_PARAM) String query);

    @GetMapping(value = "/api/3.0/users/{username}/artists/tracked.json", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Response getUserTrackedArtists(
            @RequestParam(API_KEY_PARAM) String apiKey,
            @RequestParam(PAGE_PARAM) int page,
            @PathVariable String username);

    @GetMapping(value = "/api/3.0/users/{username}/artists/tracked.json", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Response getArtistsEvents(
            @RequestParam(API_KEY_PARAM) String apiKey,
            @RequestParam(PAGE_PARAM) int page,
            @PathVariable String artistId);
}

