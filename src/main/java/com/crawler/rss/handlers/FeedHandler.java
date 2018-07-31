package com.crawler.rss.handlers;


import com.crawler.rss.data.FeedMetaData;
import com.crawler.rss.data.RssChannelFeeds;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class FeedHandler {

    private Logger logger = Logger.getLogger(FeedHandler.class);

    public FeedHandler(){
    }

    public void handleFeed(RssChannelFeeds channelFeeds, SyndFeed syndFeed) {
        logger.info("FeedHandler handleFeed start");
        List entries = syndFeed.getEntries();
        FeedMetaData feedMetaData;
        SyndEntry entry;
        for (int i = 0; i < entries.size(); i++) {
            entry = (SyndEntry)entries.get(i);
            feedMetaData = new FeedMetaData();
            feedMetaData.setAuthor(entry.getAuthor());
            feedMetaData.setLink(entry.getLink());
            feedMetaData.setPublishedDate(entry.getPublishedDate() != null ? formatDate(entry.getPublishedDate()) : "");
            feedMetaData.setTitle(entry.getTitle());
            channelFeeds.getFeedMetaDataList().add(feedMetaData);
        }
        logger.info("FeedHandler handleFeed end");

    }

    private String formatDate(Date date){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:MM");
        return format1.format(date);

    }
}
