package com.hss.scraper.service;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hss.scraper.model.ProductScrapeResultDTO;

/**
 * makes a call to DAO to get product page model objects
 * i would assume the scraping process is overnight batch and a list of urls could be passed
 * but handling single case for this test
 * @author harneksandhar
 *
 */
public interface WebScraperService {
	
	public String processScrape(String url) throws JsonGenerationException, JsonMappingException, IOException;
    public ProductScrapeResultDTO getScrapedPages(String url) throws IOException;
    public String convertDTOToJSON(Optional<ProductScrapeResultDTO> productScrape) throws JsonGenerationException, JsonMappingException, IOException;
 
}
