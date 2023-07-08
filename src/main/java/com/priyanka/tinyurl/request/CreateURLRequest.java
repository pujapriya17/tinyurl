package com.priyanka.tinyurl.request;

import java.util.UUID;

public class CreateURLRequest {

    private String api_dev_key;
    private String original_url;
    private String custom_alias;
    private UUID user_id;
    private String expire_date;

    public CreateURLRequest() {
    }

    public String getApi_dev_key() {
        return api_dev_key;
    }

    public void setApi_dev_key(String api_dev_key) {
        this.api_dev_key = api_dev_key;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public String getCustom_alias() {
        return custom_alias;
    }

    public void setCustom_alias(String custom_alias) {
        this.custom_alias = custom_alias;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }
}
