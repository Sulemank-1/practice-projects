import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubEvent(
        String type,
        Repo repo,
        Payload payload
) {}