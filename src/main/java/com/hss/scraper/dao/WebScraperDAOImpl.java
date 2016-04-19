package com.hss.scraper.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hss.scraper.model.Product;
import com.hss.scraper.model.ProductScrapeResultDTO;


/**
 * DAO to make request retrieve web page data
 * @author harneksandhar
 *
 */
public class WebScraperDAOImpl implements WebScraperDAO {
    
	/**
	 * Returns the products
	 * @throws IOException 
	 */
	public ProductScrapeResultDTO getScrapedPages(String url) throws IOException {
		String html = getHTML(url);
		List<String> productLinks = getProductLinks(html);

		return getProductScrapeResults(productLinks);
	}

	public String getHTML(String url) throws IOException {
		return Jsoup.connect(url).get().html();
	}
	
	/**
	 * Should be non blocking, so later implement a threadpool and use Callable/Futures to
	 * populate a list or update a database for later viewing....
	 *
	 * This task is a minimal get request for page data
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	public List<String> getProductLinks(String html) throws IOException {
		List<String> links = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div.productInfo a[href]");
		for (Element element : elements) {
			links.add(element.attr("href"));
		}
		return links;
	}

	
	/**
	 * scrape product page and populate product model
	 * dont want it to fall over because one link could not be parsed
	 * log it for off line viewing
	 * @param html
	 * @return
	 * @throws IOException 
	 */
	private ProductScrapeResultDTO getProductScrapeResults(List<String> urls) {
		List<Product> products = new ArrayList<>();
		for (String url : urls) {
			try {
				String html = getHTML(url);
				Product product = getProduct(html);
				products.add(product);
				
			} catch (Exception e) {
				System.out.println("LOG:Could not scrape a page:"+e);
			}			
		}
		return new ProductScrapeResultDTO(products,getTotal(products));
	}

	/**
	 * not sure which description so used meta description
	 * @param html
	 * @return
	 */
	public Product getProduct(String html) {
		Document doc = Jsoup.parse(html);
		
		String title = doc.select("div.productTitleDescriptionContainer h1").text();
		String price = doc.select("div.productSummary p.pricePerUnit").text().replaceAll("[^0-9.]", "");
		
		Element meta = doc.select("meta").first();
		String description = meta.attr("content");
		float size = getSize(html);
		return new Product(title,description,size,Float.parseFloat(price));		
	}

	public float getTotal(List<Product> products) {
		float total = 0.0f;		
		for (Product product : products) {
			total += product.getPrice();			
		}
		return total;
	}

	public float getSize(String productPage) {
		byte[] bytes = productPage.getBytes(Charset.defaultCharset());
		float size = bytes.length /1024;
		return size;
	}

	
}
