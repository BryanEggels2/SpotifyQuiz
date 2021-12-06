package com.spotifyquiz.SpotifyQuiz.services.implementations;

import com.spotifyquiz.SpotifyQuiz.models.UserDetails;
import com.spotifyquiz.SpotifyQuiz.services.interfaces.LoginInterface;
import org.apache.catalina.User;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component("loginService")
public class LoginService implements LoginInterface {

    private String accessToken = "";
    private final String client_id = "7afc8375123841d8aca2c5318acfde80";
    private final String client_secret = "8052d617886a421db36603528d3f675a";
    private final String redirect_uri = "http://localhost:8080/callback";


    @Override
    public URI login(String scope) {
        return requestUserAuthorization(scope);
    }

    @Override
    public boolean callback(String code) {
        //<base64 encoded client_id:client_secret>
        StringBuilder clientAndSecret = new StringBuilder();
        clientAndSecret.append(client_id + ':' + client_secret);

        //Base64.getEncoder().encodeToString(secret.toString().getBytes());
        String secret = Base64.getEncoder().encodeToString(clientAndSecret.toString().getBytes());

        try {
            return getAccessToken(code, secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean getAccessToken(String code , String secret) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost("https://accounts.spotify.com/api/token");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirect_uri));

        //sets body
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        //sets headers
        post.addHeader("Authorization", "Basic "+ secret);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(response.getEntity().getContent());
            System.out.println(EntityUtils.toString(response.getEntity()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private URI requestUserAuthorization(String scope){

        HttpGet httpGet = new HttpGet("https://accounts.spotify.com/authorize?");

        /*URIBuilder builder = new URIBuilder(url);
        String json = Request.Get(builder.build())
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString();
        //return the quote object.
        return new Gson().fromJson(json, UserDetails.class);*/



        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", client_id));
        urlParameters.add(new BasicNameValuePair("response_type", "code"));
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        urlParameters.add(new BasicNameValuePair("state", "random"));
        urlParameters.add(new BasicNameValuePair("scope", scope));
        try{
            URI uri = new URIBuilder(httpGet.getURI())
                    .addParameters(urlParameters)
                    .build();
            httpGet.setURI(uri);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpClientContext clientContext = HttpClientContext.create();
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet, clientContext) ) {
            System.out.println(response.toString());
            System.out.println(response.getEntity().getContent());
            System.out.println("test");
            final List<URI> redirectLocations = clientContext.getRedirectLocations();
            System.out.println(redirectLocations);
            return redirectLocations.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
