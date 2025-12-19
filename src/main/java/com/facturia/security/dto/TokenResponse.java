package com.facturia.security.dto;

public class TokenResponse {

    public String access_token;
    public String token_type;
    public long expires_in;
    public String scope;

    public TokenResponse(String access_token, String token_type, long expires_in, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.scope = scope;
    }

}
