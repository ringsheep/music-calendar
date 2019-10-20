package org.ziniakov.musiccalendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.ziniakov.musiccalendar.dto.songkick.Artist;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;

import java.util.ArrayList;
import java.util.List;

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
        int page = FIRST_PAGE;
        List<Artist> foundArtists = getUserTracked(page, username);
        List<Artist> resultingArtists = new ArrayList<>(foundArtists);

        while (!foundArtists.isEmpty()) {
            page += 1;
            foundArtists = getUserTracked(page, username);
            resultingArtists.addAll(foundArtists);
        }

        return resultingArtists;
    }

    public void save(List<Artist> artists) {
        // TODO: implement
    }

    private List<Artist> getUserTracked(int page, String username) {
        return gateway.getUserTrackedArtists(apiKey, page, username).getResultsPage().getResults().getArtists();
    }

}
