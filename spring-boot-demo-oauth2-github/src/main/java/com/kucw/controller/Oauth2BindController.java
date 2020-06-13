package com.kucw.controller;

import com.kucw.model.GithubUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Github OAuth2 官方文件 https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 */
@RequestMapping("")
@Controller
public class Oauth2BindController {

    private static final String CLIENT_ID = "c84e3e988d00b01a3cba";
    private static final String CLIENT_SECRET = "ecd565c42e2ed0298e916e5d0dad8ad9733d4db1";
    private static final String CALLBACK_URL = "http://localhost:8080/callback";

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/redirectToGithub")
    public String redirectToGithub() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + CALLBACK_URL;
    }

    @RequestMapping("/callback")
    public ModelAndView callbackWithoutScribe(@RequestParam String code) throws Exception {
        // 向 Github 取得 accessToken
        HttpPost post = new HttpPost("https://github.com/login/oauth/access_token");

        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("client_id", CLIENT_ID));
        parameters.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        parameters.add(new BasicNameValuePair("code", code));
        post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

        // 取得 response 中的 accessToken
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity, "utf-8");
        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(result);
        String accessToken = map.get("access_token");

        // 拿到 accessToken 之後，向 Github user api 取得該 user 的 data
        HttpGet get = new HttpGet("https://api.github.com/user");
        get.addHeader("Authorization", "token " + accessToken);
        HttpResponse getResponse = httpClient.execute(get);
        String json = EntityUtils.toString(getResponse.getEntity(), "utf-8");
        GithubUser githubUser = objectMapper.readValue(json, GithubUser.class);

        // 將 data 展示在前端頁面
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("githubUser", githubUser);
        return mv;
    }
}
