package org.ziniakov.musiccalendar.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/api/3.0/search/artists.json", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Response searchArtists(
            @RequestParam(API_KEY_PARAM) String apiKey,
            @RequestParam(QUERY_PARAM) String query);
}

