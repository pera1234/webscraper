package com.hss.scraper.bdd;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hss.scraper.dao.WebScraperDAOImpl;
import com.hss.scraper.model.Product;
import com.hss.scraper.model.ProductScrapeResultDTO;
import com.hss.scraper.service.WebScraperServiceImpl;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@ContextConfiguration(locations = "/test-spring-config.xml")
public class ScheduleStepDef extends AbstractJUnit4SpringContextTests {

    @Autowired
    private WebScraperServiceImpl webScraperService;
    
    @Autowired
    private WebScraperDAOImpl webScraperDAOMock;

    private List<Product> products;
    private ProductScrapeResultDTO dto;
    private String result;
    
    public void setUp() throws JsonGenerationException, JsonMappingException, IOException {
    	Mockito.reset(webScraperDAOMock);
        ReflectionTestUtils.setField(webScraperService, "webScraperDAO", webScraperDAOMock);
        Mockito.when(webScraperService.processScrape(Mockito.anyString())).thenCallRealMethod();
    }
    
    
    @Given("^the following products$")
    public void load_products(List<Product> products) throws JsonGenerationException, JsonMappingException, IOException {
        this.products = products;
    }
    
    @When("^the scrape process is called$")
    public void run_scrape() throws Exception {
    	dto = new ProductScrapeResultDTO(products, webScraperDAOMock.getTotal(products));
    	result = webScraperService.convertDTOToJSON(Optional.of(dto));
    }
        
    @Then("^valid JSON is created$")
    public void json_created() {
    	String expected = "{\"results\":[{\"title\":\"Sainsbury's Apricot Ripe & Ready x5\",\"description\":\"Buy Sainsbury's Apricot Ripe & Ready x5 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":34.0,\"unit_price\":3.5},{\"title\":\"Sainsbury's Avocado Ripe & Ready XL Loose 300g\",\"description\":\"Buy Sainsbury's Avocado Ripe & Ready XL Loose 300g online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.5},{\"title\":\"Sainsbury's Avocado, Ripe & Ready x2\",\"description\":\"Burgers are a summer must-have and these homemade ones are perfect for a barbecue, topped with cool avocado and served with oven-baked potato wedges. \",\"size\":39.0,\"unit_price\":1.8},{\"title\":\"Sainsbury's Avocados, Ripe & Ready x4\",\"description\":\"Buy Sainsbury's Avocados, Ripe & Ready x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":3.2},{\"title\":\"Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)\",\"description\":\"Buy Sainsbury's Conference Pears, Ripe & Ready x4 (minimum) online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.5},{\"title\":\"Sainsbury's Golden Kiwi x4\",\"description\":\"Buy Sainsbury's Golden Kiwi x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":35.0,\"unit_price\":1.8},{\"title\":\"Sainsbury's Kiwi Fruit, Ripe & Ready x4\",\"description\":\"Buy Sainsbury's Kiwi Fruit, Ripe & Ready x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\",\"size\":36.0,\"unit_price\":1.8}],\"total\":15.1}";
    	assertNotNull(result);
        assertThat(result,is(expected));
    }

}

