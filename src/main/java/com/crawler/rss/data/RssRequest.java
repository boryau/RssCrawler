package com.crawler.rss.data;

import java.util.List;

public class RssRequest {

    private List<String> rssList;

    public RssRequest(){
    }

    public List<String> getRssList() {
        return rssList;
    }

    public void setRssList(List<String> rssList) {
        this.rssList = rssList;
    }
}
