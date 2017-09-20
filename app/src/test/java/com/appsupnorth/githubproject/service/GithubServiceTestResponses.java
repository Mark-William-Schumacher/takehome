package com.appsupnorth.githubproject.service;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-19.
 */

public class GithubServiceTestResponses {

    public static final String userInfo200response = "{\n" +
            "    \"name\": \"The Octocat\",\n" +
            "    \"avatar_url\": \"https://avatars3.githubusercontent.com/u/583231?v=4\"\n" +
            "}";

    public static final String userRepos200response = "[\n" +
            "    {\n" +
            "        \"name\" : \"Hello-World\",\n" +
            "        \"description\" : \"My first repository on GitHub!\",\n" +
            "        \"updated_at\" : \"2017-08-14T08:08:10Z\",\n" +
            "        \"stargazers_count\": 1421,\n" +
            "        \"forks\" : 1176\n" +
            "    }\n" +
            "    \n" +
            "]";
}
