package org.ziniakov.musiccalendar.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.ziniakov.musiccalendar.controller.ArtistsController;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistsController.class)
public class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    private WireMockServer wireMockServer;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        wireMockServer = new WireMockServer(options().port(4567));
        wireMockServer.start();
        WireMock.configureFor("localhost", 4567);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnArtists() throws Throwable {
        stubFor(WireMock.get("/api/3.0/search/artists.json")
                .withQueryParam("apikey", equalTo("my-key"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("__files/songkick-api/artists_search.json")
                ));

        mockMvc.perform(get("/v1/artists/search").param("query", "Radiohead"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].id").value(253846))
                .andExpect(jsonPath("[0].uri").value("http://www.songkick.com/artists/253846-radiohead"))
                .andExpect(jsonPath("[0].displayName").value("Radiohead"));

    }
}
