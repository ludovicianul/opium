package com.insidecoding.opium.entity;

import org.springframework.http.HttpStatus;

public class OpiumCallResponse {

    private HttpStatus statusCode;
    private String responseBody;

    public OpiumCallResponse(int statusCode, String responseBody) {
        this.statusCode = HttpStatus.valueOf(statusCode);
        this.responseBody = responseBody;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
