package com.crawler.rss;

import com.crawler.rss.controllers.InputController;
import com.crawler.rss.data.RssChannels;
import com.crawler.rss.data.RssRequest;
import com.crawler.rss.data.RssResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssApplicationTests {

	public static final String ATOM_FEED_RSS = "http://www.heise.de/tp/rss/news-xl.xml";
	public static final String RSS_FEED = "http://rss.cnn.com/rss/edition.rss";
	@Autowired
	InputController inputController;

	@Autowired
	RssChannels rssChannels;

	@Test
	public void testOneRssFeed() {
		RssRequest request = new RssRequest();
		List<String> rssUrlList = new ArrayList<>();
		rssUrlList.add(ATOM_FEED_RSS);
		request.setRssList(rssUrlList);
		ResponseEntity<RssResponse> responseEntity = inputController.handleRss(request);
		assertTrue(HttpStatus.OK.equals(responseEntity.getStatusCode()));
		assertTrue(responseEntity.getBody() != null);
		assertTrue(rssChannels.getRegisteredRssChannels().containsKey("http://www.heise.de/tp/rss/news-xl.xml"));
	}

	@Test
	public void testMultipleRssFeeds() {
		RssRequest request = new RssRequest();
		List<String> rssUrlList = new ArrayList<>();
		rssUrlList.add(ATOM_FEED_RSS);
		rssUrlList.add(RSS_FEED);
		request.setRssList(rssUrlList);
		ResponseEntity<RssResponse> responseEntity = inputController.handleRss(request);
		assertTrue(HttpStatus.OK.equals(responseEntity.getStatusCode()));
		assertTrue(responseEntity.getBody() != null);
		assertTrue(rssChannels.getRegisteredRssChannels().size() == 2);
	}

	@Test
	public void testMultipleValidInvalidRssFeeds() {
		RssRequest request = new RssRequest();
		List<String> rssUrlList = new ArrayList<>();
		rssUrlList.add(ATOM_FEED_RSS);
		rssUrlList.add("http://error");
		request.setRssList(rssUrlList);
		ResponseEntity<RssResponse> responseEntity = inputController.handleRss(request);
		assertTrue(HttpStatus.OK.equals(responseEntity.getStatusCode()));
		assertTrue(responseEntity.getBody() != null);
		assertEquals(rssChannels.getNumOfFailedProcesses(), 1);
		assertTrue(rssChannels.getRegisteredRssChannels().size() == 2);
	}


	@Test
	public void testAllInvalidRssFeeds() {
		RssRequest request = new RssRequest();
		List<String> rssUrlList = new ArrayList<>();
		rssUrlList.add("http:/errorOne");
		rssUrlList.add("http://error");
		request.setRssList(rssUrlList);
		ResponseEntity<RssResponse> responseEntity = inputController.handleRss(request);
		assertTrue(HttpStatus.BAD_REQUEST.equals(responseEntity.getStatusCode()));
		assertTrue(responseEntity.getBody() != null);
		assertEquals(rssChannels.getNumOfFailedProcesses(), 2);
	}


}
