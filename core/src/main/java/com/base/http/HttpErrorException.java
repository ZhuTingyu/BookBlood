package com.base.http;

public class HttpErrorException extends RuntimeException {
    private ApiResponse responseJson;
    public HttpErrorException(ApiResponse responseJson) {
        super(responseJson!=null?responseJson.msg:"");
        this.responseJson=responseJson;
    }

    public ApiResponse getResponseJson() {
        return responseJson;
    }
}