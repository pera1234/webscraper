package com.hss.scraper.dao;

import java.io.IOException;

import com.hss.scraper.model.ProductScrapeResultDTO;

public interface WebScraperDAO {

	public ProductScrapeResultDTO getScrapedPages(String url) throws IOException;

}
