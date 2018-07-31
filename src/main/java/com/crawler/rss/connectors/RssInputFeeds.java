package com.crawler.rss.connectors;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpClientFeedFetcher;
import com.sun.syndication.io.FeedException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class RssInputFeeds implements InputFeed {

    private Logger logger = Logger.getLogger(RssInputFeeds.class);

    @Override
    public SyndFeed getFeeds(String rssUrl) throws MalformedURLException, IOException, FeedException, FetcherException  {
        logger.info("SyndFeed getFeeds start for Url "+ rssUrl);
        SyndFeed feed = null;
        FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
        FeedFetcher feedFetcher = new HttpClientFeedFetcher(feedInfoCache);
        feed = feedFetcher.retrieveFeed(new URL(rssUrl));
        logger.info("SyndFeed getFeeds end");
        return feed;
    }
}
