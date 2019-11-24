package org.ziniakov.musiccalendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ziniakov.musiccalendar.dto.domain.Event;
import org.ziniakov.musiccalendar.service.EventService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    EventService service;

    @GetMapping(path = "/list", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Event> list(int perPage, int offset) {
        return service.list(perPage, offset);
    }

    @GetMapping(path = "/refresh", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void refresh() {
        service.refresh();
    }
}
