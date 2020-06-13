package com.kucw.controller;

import com.kucw.model.TwitterUser;
import com.github.scribejava.core.services.HMACSha1SignatureService;
import com.google.common.base.Splitter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Twitter OAuth1.0a 官方文件 https://developer.twitter.com/en/docs/basics/authentication/oauth-1-0a/obtaining-user-access-tokens
 */
@RequestMapping("")
@Controller
public class Oauth1aBindController {

    private static final String CONSUMER_KEY = "CIZ7N7mJWqztIrMiClu2WWpag";
    private static final String CONSUMER_SECRET = "aSgplBUypZPZPExRGfJoxtT03g0SynYhIOxn7ZkEvIImXKxy8n";
    private static final String CALLBACK_URL = "https://c4ba298b.ngrok.io/callback";

    String requestToken;
    String requestTokenSecret;

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/bind")
    public String bind() throws Exception {
        // 向 Twitter 獲取臨時token
        HttpPost post = new HttpPost("https://api.twitter.com/oauth/request_token");

        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("oauth_callback", CALLBACK_URL));
        parameters.add(new BasicNameValuePair("oauth_consumer_key", CONSUMER_KEY));
        parameters.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
        parameters.add(new BasicNameValuePair("oauth_nonce", "1452456779")); //模擬隨機生成一個混淆字符串
        parameters.add(new BasicNameValuePair("oauth_version", "1.0"));

        String currentTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        parameters.add(new BasicNameValuePair("oauth_timestamp", currentTimestamp)); //隨機生成一個timestamp

        // 分成三部分來 encode，組合成一行 baseString 之後，最後再用 HMAC-SHA1 算法簽起來
        String verb = "POST";
        String url = URLEncoder.encode("https://api.twitter.com/oauth/request_token", "utf-8");
        String parameter = URLEncoder.encode("oauth_callback=" + URLEncoder.encode(CALLBACK_URL, "utf-8") + "&" + "oauth_consumer_key=" + CONSUMER_KEY + "&oauth_nonce=1452456779&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + currentTimestamp + "&oauth_version=1.0", "utf-8");
        String baseString = verb + "&" + url + "&" + parameter;

        parameters.add(new BasicNameValuePair("oauth_signature", generateSignature(baseString, "")));
        post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

        // 取得 response 中的臨時token
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity, "utf-8");

        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(result);
        requestToken = map.get("oauth_token");
        requestTokenSecret = map.get("oauth_token_secret");

        // redirect 到 Twitter 授權頁
        return "redirect:https://api.twitter.com/oauth/authorize?oauth_token=" + requestToken;
    }

    @RequestMapping("/callback")
    public ModelAndView callback(@RequestParam(name = "oauth_token") String oauthToken,
                                 @RequestParam(name = "oauth_verifier") String oauthVerifier) throws Exception {
        // 當 user 在 Twitter 那裡按下確認按鈕後，Twitter 就會將 user 導回這個 callback url，順便告訴我們這是哪個oauthToken的verifier

        // 使用剛剛Twitter confirmed 的 verifier 和前面申請的 臨時token 去交換 accessToken
        HttpPost post = new HttpPost("https://api.twitter.com/oauth/access_token");

        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("oauth_callback", CALLBACK_URL));
        parameters.add(new BasicNameValuePair("oauth_token", requestToken));
        parameters.add(new BasicNameValuePair("oauth_consumer_key", CONSUMER_KEY));
        parameters.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
        parameters.add(new BasicNameValuePair("oauth_nonce", "1452456779")); //模擬隨機生成一個混淆字符串
        parameters.add(new BasicNameValuePair("oauth_verifier", oauthVerifier));
        parameters.add(new BasicNameValuePair("oauth_version", "1.0"));

        String currentTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        parameters.add(new BasicNameValuePair("oauth_timestamp", currentTimestamp)); //隨機生成一個timestamp

        // 分成三部分來 encode，組合成一行 baseString 之後，最後再用 HMAC-SHA1 算法簽起來
        String verb = "POST";
        String url = URLEncoder.encode("https://api.twitter.com/oauth/access_token", "utf-8");
        String parameter = URLEncoder.encode("oauth_callback=" + URLEncoder.encode(CALLBACK_URL, "utf-8") + "&" + "oauth_consumer_key=" + CONSUMER_KEY + "&oauth_nonce=1452456779&oauth_token= " + requestToken + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + currentTimestamp + "&oauth_verifier=" + oauthVerifier + "&oauth_version=1.0", "utf-8");
        String baseString = verb + "&" + url + "&" + parameter;

        parameters.add(new BasicNameValuePair("oauth_signature", generateSignature(baseString, requestTokenSecret)));
        post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

        // 取得 response 中的 accessToken
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity, "utf-8");
        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(result);
        String accessToken = map.get("oauth_token");
        String accessTokenSecret = map.get("oauth_token_secret");

        // 直接將 accessToken 和 accessTokenSecret 展示在前端頁面，後面懶得做了...
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("twitterUser", new TwitterUser(accessToken, accessTokenSecret));
        return mv;
    }

    private String generateSignature(String baseString, String tokenSecret) {
        HMACSha1SignatureService signatureService = new HMACSha1SignatureService();
        return signatureService.getSignature(baseString, CONSUMER_SECRET, tokenSecret);
    }
}
