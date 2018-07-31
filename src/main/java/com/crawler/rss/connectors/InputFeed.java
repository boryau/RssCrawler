package com.crawler.rss.connectors;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.net.MalformedURLException;

public interface InputFeed {

    public SyndFeed getFeeds(String rssUrl) throws MalformedURLException, IOException, FeedException, FetcherException;
}
