package com.fiap.msuservideomanager.domain.model;

public class Url {
    private String url;
    private String id;

    public Url() {}

    public Url(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
