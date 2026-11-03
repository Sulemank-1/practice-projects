import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class GithubActivity {
    public static void main(String[] args) {
        getArgs(args);
    }

    public static void getArgs(String[] args){
        if (args.length == 0){
            System.out.println("No commands provided");
            return;
        }
        String username = "";
        try {
            if (args[0].equals("github-activity")){
                if (args.length < 2) {
                    System.out.println("Please provide a username.");
                    return;
                }
                username = args[1];
            }
        }catch (InputMismatchException ex){
            System.out.println("Wrong input type");
        }
        GithubActivity githubActivity = new GithubActivity();
        try {
            githubActivity.getActivity(username);
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex);
        }
    }

    public void getActivity(String username) throws IOException, InterruptedException {
        String url = "https://api.github.com/users/" + username + "/events";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            System.out.println("GitHub user '" + username + "' not found.");
            return;
        } else if (response.statusCode() == 403) {
            System.out.println("API rate limit exceeded.");
            return;
        } else if (response.statusCode() != 200) {
            System.out.println("Error: Received HTTP " + response.statusCode());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<GitHubEvent> event = mapper.readValue(response.body(), new TypeReference<>() {});

        for (GitHubEvent e: event){
            switch (e.type()){
                case "PushEvent":
                    int commitCount;
                    if (e.payload() != null && e.payload().commits() != null && !e.payload().commits().isEmpty())
                        commitCount = e.payload().commits().size();
                    else
                        commitCount = 1;

                    System.out.println(" - Pushed " + commitCount + " commit(s) to " + e.repo().name());
                break;
                case "IssuesEvent":
                    System.out.println(" - " + e.payload().action() + " an issue in " + e.repo().name());
                break;
                case "WatchEvent":
                    System.out.println(" - Starred " + e.repo().name());
                break;
                case "CreateEvent":
                    System.out.println(" - Created " + e.payload().refType() + " in " + e.repo().name());
                break;
                case "ForkEvent":
                    System.out.println("Forked " + e.repo().name());
            }
        }
    }

}
