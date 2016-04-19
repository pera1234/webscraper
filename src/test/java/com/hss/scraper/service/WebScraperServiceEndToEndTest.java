package com.hss.scraper.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Represents the client end to end test
 * 
 * Created by harneksandhar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-spring-config.xml")
public class WebScraperServiceEndToEndTest  {
	private final String URL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
	
    @Autowired
    WebScraperServiceImpl webScraperService;

    @Before
    public void setup() {
    }

    /**
     * E 2 E Test
     * @throws Exception
     */
    @Test
    public void testEndToEndToSeeIfCorrectJSONIsCreated() throws Exception {
    	String expected = "{\"results\":[{\"title\":\"Sainsbury's Apricot Ripe & Ready x5\",\"description\":\"Buy Sainsbury's Apricot Ripe & Ready x5 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":34.0,\"unit_price\":3.5},{\"title\":\"Sainsbury's Avocado Ripe & Ready XL Loose 300g\",\"description\":\"Buy Sainsbury's Avocado Ripe & Ready XL Loose 300g online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.5},{\"title\":\"Sainsbury's Avocado, Ripe & Ready x2\",\"description\":\"Burgers are a summer must-have and these homemade ones are perfect for a barbecue, topped with cool avocado and served with oven-baked potato wedges. \",\"size\":39.0,\"unit_price\":1.8},{\"title\":\"Sainsbury's Avocados, Ripe & Ready x4\",\"description\":\"Buy Sainsbury's Avocados, Ripe & Ready x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":3.2},{\"title\":\"Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)\",\"description\":\"Buy Sainsbury's Conference Pears, Ripe & Ready x4 (minimum) online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.5},{\"title\":\"Sainsbury's Golden Kiwi x4\",\"description\":\"Buy Sainsbury's Golden Kiwi x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.8},{\"title\":\"Sainsbury's Kiwi Fruit, Ripe & Ready x4\",\"description\":\"Buy Sainsbury's Kiwi Fruit, Ripe & Ready x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":36.0,\"unit_price\":1.8}],\"total\":15.1}";
    	
    	String JSON = webScraperService.processScrape(URL);
    	assertEquals(expected,JSON);
    }
    
}
