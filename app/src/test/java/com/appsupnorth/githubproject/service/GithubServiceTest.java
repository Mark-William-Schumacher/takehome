package com.appsupnorth.githubproject.service;

import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


import static org.junit.Assert.*;


public class GithubServiceTest {


    private MockWebServer mockWebServer;
    private GithubService.GithubApi githubService;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String endpoint = mockWebServer.url("/").toString();
        githubService = GithubService.create(endpoint);
    }

    @Test
    public void userInfoCall200() throws Exception {

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(
                    GithubServiceTestResponses.userInfo200response
                ));

        githubService.userInfo("test")
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<GithubUser>() {
                    @Override
                    public void call(GithubUser githubUser) {
                        assertEquals(githubUser.name, "The Octocat");
                        assertEquals(githubUser.avatar_url, "https://avatars3.githubusercontent.com/u/583231?v=4");
                    }
                });
    }

    @Test
    public void userRepoCall200() throws Exception {

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(
                        GithubServiceTestResponses.userRepos200response
                ));


        githubService.userRepos("test")
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<List<GithubRepo>>() {
                    @Override
                    public void call(List<GithubRepo> githubRepos) {
                        assertEquals(githubRepos.size(), 1);
                        assertEquals(githubRepos.get(0).forks, Integer.valueOf(1176));
                        assertEquals(githubRepos.get(0).stargazers_count, Integer.valueOf(1421));
                        assertEquals(githubRepos.get(0).updated_at, "2017-08-14T08:08:10Z");
                        assertEquals(githubRepos.get(0).description, "My first repository on GitHub!");
                    }
                });
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}