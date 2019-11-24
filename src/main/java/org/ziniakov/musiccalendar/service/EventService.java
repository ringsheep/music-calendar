package org.ziniakov.musiccalendar.service;

import org.springframework.beans.factory.annotation.Value;
import org.ziniakov.musiccalendar.dto.domain.Artist;
import org.ziniakov.musiccalendar.dto.domain.Event;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;
import org.ziniakov.musiccalendar.mapper.EventMapper;
import org.ziniakov.musiccalendar.repository.ArtistRepository;
import org.ziniakov.musiccalendar.repository.EventRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventService {


    @Value("${songkick.api-key}")
    private String apiKey;

    EventRepository eventRepository;
    ArtistRepository artistRepository;
    SongkickGateway songkickGateway;
    EventMapper eventMapper;

    public List<Event> list(int perPage, int offset) {
        return eventRepository.findAll(perPage, offset);
    }

    public void refresh() {
        List<Artist> artists = artistRepository.findAll();
        artists.stream()
                .map(artist -> getAllArtistsEvents(0, artist.getId()))
                .map(events -> events.stream().map(eventMapper::mapToDomainEvent).collect(Collectors.toList()))
                .forEach(events -> eventRepository.saveAll(events));

    }

    private List<org.ziniakov.musiccalendar.dto.songkick.Event> getAllArtistsEvents(int startPage, String artist) {
        List<org.ziniakov.musiccalendar.dto.songkick.Event> foundEvents = songkickGateway.getArtistsEvents(apiKey, startPage, artist).getResultsPage().getResults().getEvents();
        List<org.ziniakov.musiccalendar.dto.songkick.Event> resultingEvents = new ArrayList<>();

        if (!Optional.ofNullable(foundEvents).orElse(Collections.emptyList()).isEmpty()) {
            resultingEvents.addAll(foundEvents);
            resultingEvents.addAll(getAllArtistsEvents(startPage + 1, artist));
        }

        return resultingEvents;
    }

}
