package org.ziniakov.musiccalendar.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.ziniakov.musiccalendar.dto.songkick.Artist;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;
import org.ziniakov.musiccalendar.mapper.ArtistMapper;
import org.ziniakov.musiccalendar.repository.ArtistRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistsService {

    private static final int FIRST_PAGE = 1;

    @Value("${songkick.api-key}")
    private String apiKey;

    private final SongkickGateway gateway;

    private final ArtistMapper artistMapper = Mappers.getMapper(ArtistMapper.class);

    private final ArtistRepository artistRepository;

    public List<Artist> search(String query) {
        return gateway.searchArtists(apiKey, FIRST_PAGE, query).getResultsPage().getResults().getArtists();
    }

    public List<Artist> getUserTracked(String username) {
        return getUserTracked(FIRST_PAGE, username);
    }

    public void save(List<Artist> artists) {
        artistRepository.saveAll(
                artists.stream()
                        .map(artistMapper::mapToJpaArtist)
                        .collect(Collectors.toList())
        );
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
