package org.ziniakov.musiccalendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ziniakov.musiccalendar.dto.songkick.Artist;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventsController {

    @GetMapping(path = "/list", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Artist> list(int perPage, int offset) {
        // TODO: implement
        return Collections.emptyList();
    }

    @GetMapping(path = "/refresh", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void refresh() {
        // TODO: implement
    }
}
