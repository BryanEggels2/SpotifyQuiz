package com.spotifyquiz.SpotifyQuiz.models;

public class UserDetails {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static void setAccess_token(String access_token) {
        UserDetails.access_token = access_token;
    }

    public static String access_token = "BQBpXdsI_UO_N1pnkSXDZYatuUOt88I8bW6tcmntxMvpNofmw6hz6mZo25hA7iAlVXu1_WFsnKkPPwNc5cgFNDjOo8u1duioM5YrR5Un9oLL6mY-lbhbX6JpxQ_Dd1WoCYCuEBk257ahs_TNTpjNEwQVMqCA7xgtzg-X5tfHMTWMW9BPvQuvrFUxE3e3ysXnImiLrPpA";
    public static String refresh_token = "AQBjLeaDM40be1BUkmQs_KZUb7sx0sIjhdp-UpEsWmfrFVQk4rURiPIOQLHB8HGlvehYA8lP86TuTEj3Wb9ZUDmk5lle5npaDWmv4vw7HVp7D_cEGRZcc8s1dWdVmgkgYSY";

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }
}
