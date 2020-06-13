package com.kucw.controller;

import com.kucw.model.TwitterUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Twitter OAuth1.0a 官方文件 https://developer.twitter.com/en/docs/basics/authentication/oauth-1-0a/obtaining-user-access-tokens
 * 使用 scribe library 輔助我們進行 OAuth call
 */
@RequestMapping("/scribe")
@Controller
public class Oauth1aBindWithScribeController {

    private static final String CONSUMER_KEY = "CIZ7N7mJWqztIrMiClu2WWpag";
    private static final String CONSUMER_SECRET = "aSgplBUypZPZPExRGfJoxtT03g0SynYhIOxn7ZkEvIImXKxy8n";

    private OAuth10aService twitterOAuthService = new ServiceBuilder(CONSUMER_KEY)
            .apiSecret(CONSUMER_SECRET)
            .callback("https://c4ba298b.ngrok.io/callback")
            .build(TwitterApi.instance());

    private OAuth1RequestToken requestToken;

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/bind")
    public String bind() throws Exception {
        // 向 Twitter 獲取臨時token
        requestToken = twitterOAuthService.getRequestToken();

        // redirect 到 Twitter 授權頁
        return "redirect:" + twitterOAuthService.getAuthorizationUrl(requestToken);
    }

    @RequestMapping("/callback")
    public ModelAndView callback(@RequestParam(name = "oauth_token") String oauthToken,
                           @RequestParam(name = "oauth_verifier") String oauthVerifier) throws Exception {
        // 當 user 在 Twitter 那裡按下確認按鈕後，Twitter 就會將 user 導回這個 callback url，順便告訴我們這是哪個oauthToken的verifier

        // 使用剛剛Twitter confirmed 的 verifier 和前面申請的 臨時token 去交換 accessToken
        OAuth1AccessToken accessToken = twitterOAuthService.getAccessToken(requestToken, oauthVerifier);

        // 拿到 accessToken 之後，向 Twitter api 取得該 user 的 data
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/settings.json");
        twitterOAuthService.signRequest(accessToken, request);
        Response response = twitterOAuthService.execute(request);
        String json = response.getBody();
        TwitterUser twitterUser = objectMapper.readValue(json, TwitterUser.class);

        // 將 data 展示在前端頁面
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("twitterUser", twitterUser);
        return mv;
    }
}
