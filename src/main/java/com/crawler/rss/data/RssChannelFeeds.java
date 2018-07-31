package com.crawler.rss.data;

import java.util.ArrayList;
import java.util.List;

public class RssChannelFeeds {

    private String channelName;

    private String channelUrl;

    private List<FeedMetaData> feedMetaDataList = new ArrayList<>();

    public RssChannelFeeds(){

    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<FeedMetaData> getFeedMetaDataList() {
        return feedMetaDataList;
    }

    public void setFeedMetaDataList(List<FeedMetaData> feedMetaDataList) {
        this.feedMetaDataList = feedMetaDataList;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }
}
