package org.ziniakov.musiccalendar.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.ziniakov.musiccalendar.controller.ArtistsController;
import org.ziniakov.musiccalendar.dto.songkick.Response;
import org.ziniakov.musiccalendar.gateway.SongkickGateway;

import java.io.File;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistsController.class)
public class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SongkickGateway songkickGateway;

    @Test
    public void shouldReturnArtists() throws Throwable {
        Response response = objectMapper.readValue(readFileFromResources("__files/songkick-api/artists_search.json"), Response.class);
        doReturn(response).when(songkickGateway).searchArtists("api-key", "Radiohead");

        mockMvc.perform(get("/v1/artists/search")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .param("query", "Radiohead"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].id").value(253846))
                .andExpect(jsonPath("[0].uri").value("http://www.songkick.com/artists/253846-radiohead"))
                .andExpect(jsonPath("[0].displayName").value("Radiohead"));

    }

    @SneakyThrows
    private File readFileFromResources(String relativePath) {
        return new File("src/test/resources/" + relativePath);
    }
}
