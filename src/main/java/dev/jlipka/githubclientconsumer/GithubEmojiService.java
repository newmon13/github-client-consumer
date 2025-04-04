package dev.jlipka.githubclientconsumer;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GithubEmojiService {
    private final GithubApiClient githubApiClient;

    public GithubEmojiService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    public Map<String, String> getGithubEmojis() {
        return githubApiClient.getEmojisAvailableOnGithub();
    }
}
