package org.ziniakov.musiccalendar.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class SongkickApiStub {

    void stub() {
        stubFor(get("/api/3.0/search/artists.json")
                .withQueryParam("apikey", equalTo("my-key"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("__files/songkick-api/artists_search.json")
                ));
    }
}
