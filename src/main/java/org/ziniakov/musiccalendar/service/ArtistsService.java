package org.ziniakov.musiccalendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.ziniakov.musiccalendar.dto.songkick.Artist;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistsService {

    private static final int FIRST_PAGE = 1;

    @Value("${songkick.api-key}")
    private String apiKey;

    private final SongkickGateway gateway;

    public List<Artist> search(String query) {
        return gateway.searchArtists(apiKey, FIRST_PAGE, query).getResultsPage().getResults().getArtists();
    }

    public List<Artist> getUserTracked(String username) {
        return getUserTracked(FIRST_PAGE, username);
    }

    public void save(List<Artist> artists) {
        // TODO: implement
    }

    private List<Artist> getUserTracked(int startPage, String username) {
        List<Artist> foundArtists = gateway.getUserTrackedArtists(apiKey, startPage, username).getResultsPage().getResults().getArtists();
        List<Artist> resultingArtists = new ArrayList<>();

        if (!Optional.ofNullable(foundArtists).orElse(Collections.emptyList()).isEmpty()) {
            resultingArtists.addAll(foundArtists);
            resultingArtists.addAll(getUserTracked(startPage + 1, username));
        }

        return resultingArtists;
    }

}
