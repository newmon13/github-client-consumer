package dev.jlipka.githubclientconsumer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class GithubApiClient {

    private final RestClient restClient;

    @Value("${github.url}")
    private String GITHUB_API_URL;

    public GithubApiClient() {
        this.restClient = RestClient.create();
    }

    public Map<String, String> getEmojisAvailableOnGithub() {
        return restClient.get()
                .uri(GITHUB_API_URL + "/emojis")
                .retrieve()
                .body(Map.class);
    }
}
