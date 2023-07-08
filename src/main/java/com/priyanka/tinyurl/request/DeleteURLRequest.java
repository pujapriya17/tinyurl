package com.priyanka.tinyurl.request;

public class DeleteURLRequest {

    private String api_dev_key;
    private String url_key;

    public DeleteURLRequest() {
    }

    public String getApi_dev_key() {
        return api_dev_key;
    }

    public void setApi_dev_key(String api_dev_key) {
        this.api_dev_key = api_dev_key;
    }

    public String getUrl_key() {
        return url_key;
    }

    public void setUrl_key(String url_key) {
        this.url_key = url_key;
    }
}
