package com.hss.scraper.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hss.scraper.dao.WebScraperDAOImpl;
import com.hss.scraper.model.Product;
import com.hss.scraper.model.ProductScrapeResultDTO;

/**
 * This service is called to start the calls to retrieve the product data
 * It will apply business logic e.g. sum the prices and convert to JSON
 * @author harneksandhar
 *
 */
public class WebScraperServiceImpl implements WebScraperService{
    @Autowired
    private WebScraperDAOImpl webScraperDAO;

    @Override
    public String processScrape(String url) throws JsonGenerationException, JsonMappingException, IOException {
    	Optional<ProductScrapeResultDTO> scrapeResult = Optional.ofNullable(getScrapedPages(url));
		return convertDTOToJSON(scrapeResult);			
    }
    
    @Override
	public ProductScrapeResultDTO getScrapedPages(String url) {
		try {
			return webScraperDAO.getScrapedPages(url);
		} catch (IOException e) {
			System.out.println("Backend error occurred:"+e.getMessage());
		}
		return null; 
	}
    
    
    /**
     * Performs the JSON serialization
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    @Override
	public String convertDTOToJSON(Optional<ProductScrapeResultDTO> productScrape) throws JsonGenerationException, JsonMappingException, IOException {
    	if (productScrape.isPresent()) {
    		ProductScrapeResultDTO productScrapeResult = productScrape.get();
    		final StringWriter writer = new StringWriter();
    	    final ObjectMapper mapper = new ObjectMapper();

    	    mapper.writeValue(writer, productScrapeResult);
    	    String JSONOutput = mapper.writeValueAsString(productScrapeResult);
    	    System.out.println("LOG - JSON output:\n"+JSONOutput);
    		return JSONOutput;
		}    	
    	return null;
	}
	
	public String getSum(List<Product> productPages) {
		 return null;
	}

}
