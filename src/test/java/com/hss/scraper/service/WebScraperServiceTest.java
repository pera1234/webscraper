package com.hss.scraper.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.hss.scraper.dao.WebScraperDAOImpl;
import com.hss.scraper.model.Product;
import com.hss.scraper.model.ProductScrapeResultDTO;

/**
 * Represents the client that will receive serialized json results
 * 
 * Created by harneksandhar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-spring-config.xml")
public class WebScraperServiceTest  {
	
	@Autowired
    private WebScraperDAOImpl webScraperDAOMock;

    @Autowired
    WebScraperServiceImpl webScraperService;


    @Before
    public void setup() {
       Mockito.reset(webScraperDAOMock);
       ReflectionTestUtils.setField(webScraperService, "webScraperDAO", webScraperDAOMock);
    }
    
    @Test
    public void testThatProductPageUrlsAreParsedFromHTML() throws Exception {
    	String result = "{\"results\":[{\"title\":\"Sainsbury's Apricot Ripe & Ready x5\",\"description\":\"Buy Sainsbury's Apricot Ripe & Ready x5 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":34.0,\"unit_price\":3.5},{\"title\":\"Sainsbury's Avocado Ripe & Ready XL Loose 300g\",\"description\":\"Buy Sainsbury's Avocado Ripe & Ready XL Loose 300g online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.5}],\"total\":5.0}";
    	List<Product> products = Arrays.asList(new Product("Sainsbury's Apricot Ripe & Ready x5","Buy Sainsbury's Apricot Ripe & Ready x5 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.",34.0f,3.50f),
    			new Product("Sainsbury's Avocado Ripe & Ready XL Loose 300g","Buy Sainsbury's Avocado Ripe & Ready XL Loose 300g online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.",35.0f,1.50f));

    	ProductScrapeResultDTO dto = new ProductScrapeResultDTO(products, 5.0f);	
    	Mockito.when(webScraperDAOMock.getScrapedPages(Mockito.anyString())).thenReturn(dto);
            
        String json = webScraperService.processScrape("");
        System.out.println();
        assertThat(json, is(result));
    }

    
}
