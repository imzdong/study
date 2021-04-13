package org.imzdong.study.feign;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;

import java.util.List;

/**
 * @author admin
 * @date 2021/4/12
 */
public class FeignDemo {

    public static void main(String[] args) {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");
        // Fetch and print a list of the contributors to this library.
        List<Contributor> contributors = github.contributors("imzdong", "study");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

    private interface GitHub {
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

        @RequestLine("POST /repos/{owner}/{repo}/issues")
        void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);

    }

    private class Contributor {
        String login;
        int contributions;
    }

    private class Issue {
        String title;
        String body;
        List<String> assignees;
        int milestone;
        List<String> labels;
    }
}
