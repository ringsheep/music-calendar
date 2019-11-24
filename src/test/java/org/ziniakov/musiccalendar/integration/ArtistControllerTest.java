package org.ziniakov.musiccalendar.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.ziniakov.musiccalendar.controller.ArtistsController;
import org.ziniakov.musiccalendar.dto.songkick.Response;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;
import org.ziniakov.musiccalendar.repository.ArtistsRepository;
import org.ziniakov.musiccalendar.service.ArtistsService;

import java.io.File;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistsController.class)
@Import(ArtistsService.class)
public class ArtistControllerTest {

    public static final String API_KEY = "api-key";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SongkickGateway songkickGateway;

    @MockBean
    ArtistsRepository artistsRepository;

    @Test
    public void search_should_return_artists() throws Throwable {
        Response response = objectMapper.readValue(readFileFromResources("__files/songkick-api/artists_search.json"), Response.class);
        doReturn(response).when(songkickGateway).searchArtists(API_KEY, 1, "Radiohead");

        mockMvc.perform(get("/v1/artists/search")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .param("page", "1")
                .param("query", "Radiohead"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].id").value(253846))
                .andExpect(jsonPath("[0].uri").value("http://www.songkick.com/artists/253846-radiohead"))
                .andExpect(jsonPath("[0].displayName").value("Radiohead"));

    }

    @Test
    public void getUserTracked_should_return_tracked_artists() throws Throwable {
        setupGetUserTrackedArtistsResponse(1, "__files/songkick-api/artists_tracked_page_1.json");
        setupGetUserTrackedArtistsResponse(2, "__files/songkick-api/artists_tracked_page_2.json");
        setupGetUserTrackedArtistsResponse(3, "__files/songkick-api/artists_tracked_empty.json");

        mockMvc.perform(get("/v1/artists/getUserTracked")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .param("username", "ringsheep"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(100)))
                .andExpect(jsonPath("[0].id").value(446407))
                .andExpect(jsonPath("[0].uri").value("http://www.songkick.com/artists/446407-23-skidoo?utm_source=59058&utm_medium=partner"))
                .andExpect(jsonPath("[0].displayName").value("23 Skidoo"));

    }

    @SneakyThrows
    private void setupGetUserTrackedArtistsResponse(int page, String filePath) {
        Response response = objectMapper.readValue(readFileFromResources(filePath), Response.class);
        doReturn(response).when(songkickGateway).getUserTrackedArtists(API_KEY, page, "ringsheep");
    }

    @SneakyThrows
    private File readFileFromResources(String relativePath) {
        return new File("src/test/resources/" + relativePath);
    }
}
