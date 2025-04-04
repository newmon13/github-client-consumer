package dev.jlipka.githubclientconsumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WireMockTest(httpPort = 8081)
class GithubEmojiServiceTest {
    private static final UrlPattern EMOJIS_URL_PATTERN =
            urlPathMatching("/emojis");

    @Autowired
    GithubEmojiService githubEmojiService;

    @Test
    void getGithubEmojis_ShouldReturnAllGithubEmojis() {
        //given
        JsonNode jsonNode = getExampleGithubEmojiResponse();
        stubFor(get(EMOJIS_URL_PATTERN).willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withJsonBody(jsonNode)));

        //when
        Map<String, String> githubEmojis = githubEmojiService.getGithubEmojis();

        //then
        assertAll(
                () -> assertThat(githubEmojis).isNotNull(),
                () -> assertThat(githubEmojis).isNotEmpty(),
                () -> assertThat(githubEmojis.size()).isEqualTo(1),
                () -> {
                    verify(getRequestedFor(EMOJIS_URL_PATTERN)
                    );
                });
    }

    private JsonNode getExampleGithubEmojiResponse() {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.valueToTree(
                Map.of("8ball", "Link to emoji"));
    }
}