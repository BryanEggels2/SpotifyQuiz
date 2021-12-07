package com.spotifyquiz.SpotifyQuiz.services.implementations;

import com.google.gson.Gson;
import com.spotifyquiz.SpotifyQuiz.models.UserDetails;
import com.spotifyquiz.SpotifyQuiz.services.interfaces.LoginInterface;
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
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Component("loginService")
public class LoginService implements LoginInterface {

    //TODO: secure this so it isn't available on git.


    @Override
    public URI login(String scope) {
        return requestUserAuthorization(scope);
    }

    @Override
    public UserDetails callback(String code) {
        //<base64 encoded client_id:client_secret>
        String s = PropertiesReader.getProperty(Apikeys.client_id) + ':' +
                PropertiesReader.getProperty(Apikeys.client_secret);

        String secret = Base64.getEncoder().encodeToString(s.getBytes());

        try {
            return getAccessToken(code, secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserDetails getAccessToken(String code , String secret) throws UnsupportedEncodingException {
        //using httppost because you can't set the headers with apache fluent.
        HttpPost post = new HttpPost("https://accounts.spotify.com/api/token");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("redirect_uri", PropertiesReader.getProperty(Apikeys.redirect_uri)));

        //sets body
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        //sets headers
        post.addHeader("Authorization", "Basic "+ secret);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(response.getEntity().getContent());
            System.out.println();
            return new Gson().fromJson(EntityUtils.toString(response.getEntity()), UserDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URI requestUserAuthorization(String scope){
        //We are using the original HttpGet here to get the redirectionlocations later on. The Apache fluent library can't do this.
        HttpGet httpGet = new HttpGet("https://accounts.spotify.com/authorize?");

        String state = generateRandomState(15);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", PropertiesReader.getProperty(Apikeys.client_id)));
        urlParameters.add(new BasicNameValuePair("response_type", "code"));
        urlParameters.add(new BasicNameValuePair("redirect_uri", PropertiesReader.getProperty(Apikeys.redirect_uri)));
        urlParameters.add(new BasicNameValuePair("state", state));
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
            final List<URI> redirectLocations = clientContext.getRedirectLocations();
            System.out.println(redirectLocations);
            return redirectLocations.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String generateRandomState(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


}
