package com.crawler.rss.controllers;


import com.crawler.rss.connectors.InputFeed;
import com.crawler.rss.data.*;
import com.crawler.rss.handlers.FeedHandler;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping(value = "rss")
public class InputController {

    @Autowired
    RssChannels rssChannels;

    @Autowired
    InputFeed inputFeed;

    @Autowired
    FeedHandler feedHandler;

    private Logger logger = Logger.getLogger(InputController.class);


    @RequestMapping(value= "getAllChannels", method = RequestMethod.GET)
    public @ResponseBody List<String> getAllChannels() {
        logger.info("InputController getAllChannels start");
        List<String> reponse = rssChannels.getRegisteredRssChannels().keySet().stream().collect(toList());
        logger.info("InputController getAllchannels end");
        return reponse;
    }

    @RequestMapping(value= "getRssFeeds", method = RequestMethod.GET)
    public @ResponseBody List<FeedMetaData> getRssFeeds(@RequestParam("rssName") String rssName) {
        logger.info("InputController getRssFeeds start for rss "+ rssName);
        RssChannelFeeds channelFeeds = rssChannels.getRegisteredRssChannels().get(rssName);
        logger.info("InputController getRssFeeds end");
        return channelFeeds.getFeedMetaDataList();
    }


    @RequestMapping(value = "check", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<RssResponse> handleRss(@RequestBody RssRequest rssRequest){
        logger.info("InputController handleRss start");
        RssResponse output = new RssResponse();
        rssChannels.setNumOfFailedProcesses(0);
        rssRequest.getRssList().forEach( rssUrl -> {
                    try {
                        logger.info("InputController handleRss url is " + rssUrl);
                        SyndFeed syndFeed = inputFeed.getFeeds(rssUrl);
                        RssChannelFeeds channelFeeds = new RssChannelFeeds();
                        if(syndFeed == null || syndFeed.getEntries() == null || syndFeed.getEntries().size() == 0){
                            logger.info("No feeds found for " + rssUrl);
                            rssChannels.getRegisteredRssChannels().put(rssUrl, channelFeeds);
                            return;
                        }
                        channelFeeds.setChannelName(syndFeed.getTitle());
                        channelFeeds.setChannelUrl(rssUrl);
                        feedHandler.handleFeed(channelFeeds, syndFeed);
                        rssChannels.getRegisteredRssChannels().put(rssUrl, channelFeeds);
                    } catch (MalformedURLException exception) {
                        logger.error("Malformed Url received" , exception);
                        rssChannels.increaseNumOfFailedProcesses(1);
                    } catch (IOException | FeedException | FetcherException feedException) {
                        logger.error("Feed crawling failed", feedException);
                        rssChannels.increaseNumOfFailedProcesses(1);
                    }catch(Exception generalException){
                        logger.error("General Exception occurde", generalException);
                        rssChannels.increaseNumOfFailedProcesses(1);
                    }
                }
        );

        if(rssChannels.getNumOfFailedProcesses() == rssRequest.getRssList().size()) {
            output.setCode(400);
            output.setMessage("All Rss urls were not hanlded");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
        }
        output.setCode(200);
        output.setMessage("Rss url were processed");
        logger.info("InputController handleRss end");
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}




