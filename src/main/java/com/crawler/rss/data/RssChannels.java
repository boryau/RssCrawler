package com.crawler.rss.data;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RssChannels {
    private Map<String, RssChannelFeeds> registeredRssChannels = new ConcurrentHashMap<>();

    private int numOfFailedProcesses = 0;

    public RssChannels(){

    }

    public Map<String, RssChannelFeeds> getRegisteredRssChannels() {
        return registeredRssChannels;
    }

    public void setRegisteredRssChannels(Map<String, RssChannelFeeds> registeredRssChannels) {
        this.registeredRssChannels = registeredRssChannels;
    }

    public int getNumOfFailedProcesses() {
        return numOfFailedProcesses;
    }

    public void setNumOfFailedProcesses(int numOfFailedProcesses) {
        this.numOfFailedProcesses = numOfFailedProcesses;
    }

    public void increaseNumOfFailedProcesses(int num) {
        numOfFailedProcesses = numOfFailedProcesses + num;
    }
}
