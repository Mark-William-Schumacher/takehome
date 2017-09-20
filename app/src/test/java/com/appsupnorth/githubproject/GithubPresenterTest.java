package com.appsupnorth.githubproject;

import com.appsupnorth.githubproject.service.GithubService;
import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;
import com.appsupnorth.githubproject.views.GithubView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.plugins.RxJavaTestPlugins;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-20.
 */

@RunWith(MockitoJUnitRunner.class)
public class GithubPresenterTest {

    @Mock GithubService.GithubApi mockApi;
    @Mock
    GithubView mockView;

    GithubPresenter presenter;

    @Before
    public void setUp() throws Exception {

        presenter = new GithubPresenter(mockView, mockApi);

        RxJavaTestPlugins.resetPlugins();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }


    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }


    @Test
    public void testSearchButtonClicked(){

        GithubUser user = GithubPresenterTestHelper.mockUser();

        when(mockApi.userInfo(anyString())).thenReturn( Observable.just(user) );

        presenter.searchButtonClicked("TEST");

        verify(mockView).showHeader(user);

    }

    @Test
    public void testGetRepositories(){

        List<GithubRepo> mockRepos = GithubPresenterTestHelper.mockRepos();

        when(mockApi.userRepos(anyString())).thenReturn( Observable.just(mockRepos) );

        presenter.getRepositories("TEST");

        verify(mockView).showRepositories(mockRepos);

    }

    @Test
    public void testRepositoryClicked(){
        List<GithubRepo> mockRepos = GithubPresenterTestHelper.mockRepos();

        presenter.repositoryClicked(mockRepos.get(0));

        verify(mockView).showBottomSheet(mockRepos.get(0));
    }

    @Test
    public void testRepofail() throws Exception{
        MockWebServer mockWebServer =  new MockWebServer();
        mockWebServer.start();
        String endpoint = mockWebServer.url("/").toString();
        GithubService.GithubApi githubService = GithubService.create(endpoint);
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(
                        "BAD"
                ));
        GithubPresenter presenterFail = new GithubPresenter(mockView,githubService);

        presenterFail.getRepositories("TEST");

        verify(mockView).setSearchButtonClickable(true);

    }


    @Test
    public void testSearchFailed() throws Exception{
        MockWebServer mockWebServer =  new MockWebServer();
        mockWebServer.start();
        String endpoint = mockWebServer.url("/").toString();
        GithubService.GithubApi githubService = GithubService.create(endpoint);
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(
                        "BAD"
                ));
        GithubPresenter presenterFail = new GithubPresenter(mockView,githubService);

        presenterFail.searchButtonClicked("TEST");

        verify(mockView).setSearchButtonClickable(true);

    }





}



