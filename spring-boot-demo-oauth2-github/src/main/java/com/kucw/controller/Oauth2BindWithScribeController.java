package com.kucw.controller;

import com.kucw.model.GithubUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Github OAuth2 官方文件 https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 * 使用 scribe library 輔助我們進行 OAuth call
 */
@RequestMapping("/scribe")
@Controller
public class Oauth2BindWithScribeController {

    private static final String CLIENT_ID = "c84e3e988d00b01a3cba";
    private static final String CLIENT_SECRET = "ecd565c42e2ed0298e916e5d0dad8ad9733d4db1";
    private static final String CALLBACK_URL = "http://localhost:8080/callback";

    private OAuth20Service githubOAuthService = new ServiceBuilder(CLIENT_ID).apiSecret(CLIENT_SECRET).build(GitHubApi.instance());

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/redirectToGithub")
    public String redirectToGithub() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", CLIENT_ID);
        paramMap.put("redirect_uri", CALLBACK_URL);
        return "redirect:" + githubOAuthService.getAuthorizationUrl(paramMap);
    }

    @RequestMapping("/callback")
    public ModelAndView callback(@RequestParam String code) throws Exception {
        // 向 Github 取得 accessToken
        OAuth2AccessToken accessToken = githubOAuthService.getAccessToken(code);

        // 拿到 accessToken 之後，向 Github user api 取得該 user 的 data
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
        githubOAuthService.signRequest(accessToken, request);
        Response response = githubOAuthService.execute(request);
        String json = response.getBody();
        GithubUser githubUser = objectMapper.readValue(json, GithubUser.class);

        // 將 data 展示在前端頁面
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("githubUser", githubUser);
        return mv;
    }
}
