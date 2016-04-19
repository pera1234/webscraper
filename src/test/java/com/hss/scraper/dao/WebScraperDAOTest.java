package com.hss.scraper.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hss.scraper.dao.WebScraperDAOImpl;
import com.hss.scraper.model.Product;

/**
 * DAO tests - maybe use html unit
 * 
 * Created by harneksandhar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-spring-config.xml")
public class WebScraperDAOTest  {
	
	@Autowired
    private WebScraperDAOImpl webScraperDAO;

    @Before
    public void setup() {
   }
    
    @Test
    public void testThatProductPageUrlsAreParsedFromHTML() throws Exception {
    	  String HTML = "<html><div class=\"productInfo\"> <h3> <a href=\"http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html\" > "
    	  		+ "Sainsbury's Apricot Ripe & Ready x5 <img src=\"http://c2.sainsburys.co.uk/wcsstore7.11.1.161/SainsburysStorefrontAssetStore/wcassets/product_images/media_7572754_M.jpg\" alt=\"\" /> </a> </h3> </div> "
    	  		+ "<div class=\"productInfo\"> <h3> <a href=\"http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-avocado-xl-pinkerton-loose-300g.html\" > Sainsbury's Avocado Ripe & Ready XL Loose 300g "
    	  		+ "<img src=\"http://c2.sainsburys.co.uk/wcsstore7.11.1.161/ExtendedSitesCatalogAssetStore/images/catalog/productImages/51/0000000202251/0000000202251_M.jpeg\" alt=\"\" /> </a> </h3> </div></html>";
    	  
          List<String> urls = webScraperDAO.getProductLinks(HTML);
          assertThat(urls.size(),is(2));
          assertThat(urls.get(0), is("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html"));
          assertThat(urls.get(1), is("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-avocado-xl-pinkerton-loose-300g.html"));
    }

    @Test
    public void testThatProductModelIsCreatedCorrectlyFromHTML() throws Exception {
    	  String HTML = "<html><meta name=\"description\" content=\"Buy Sainsbury&#039;s Apricot Ripe &amp; Ready x5 online from Sainsbury&#039;s,"
    	  		+ " the same great quality, freshness and choice you&#039;d find in store. Choose from 1 hour delivery slots and collect Nectar points.\"/> "
    	  		+ "<meta name=\"keyword\" content=\"\"/> <meta property=\"fb:app_id\" content=\"258691960829999\" /> "
    	  		+ "<meta property=\"og:type\" content=\"product\" /> <meta property=\"og:url\" content=\"http://www.sainsburys.co.uk/shop/gb/groceries/sainsburys-conference-pears--ripe"
    	  		+ "---ready-x4-%28minimum%29\" /> <meta property=\"og:title\" content=\"Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)\" /> "
    	  		+ "<meta property=\"og:image\" content=\"http://www.sainsburys.co.uk/wcsstore7.11.1.161/ExtendedSitesCatalogAssetStore/images/catalog/productImages/08/0000001514308/0000001514308_L.jpeg\" /> "
    	  		+ "<meta property=\"og:site_name\" content=\"Sainsbury's\" /> <link rel=\"canonical\" href=\"http://www.sainsburys.co.uk/shop/gb/groceries/sainsburys-conference-pears--ripe---ready-x4-%28minimum%29\" />"
    	  		+ " <!-- BEGIN CommonCSSToInclude.jspf --><!--[if IE 8]> <link type=\"text/css\" href=\"http://c1.sainsburys.co.uk/wcsstore7.11.1.161/SainsburysStorefrontAssetStore/css/main-ie8.min.css\" rel=\"stylesheet\" media=\"all\" /> <![endif]--> "
    	  		+ "<!--[if !IE 8]><!--> <link type=\"text/css\" href=\"http://c1.sainsburys.co.uk/wcsstore7.11.1.161/SainsburysStorefrontAssetStore/css/main.min.css\" rel=\"stylesheet\" media=\"all\" /> <!--<![endif]-->"
    	  		+ " <link type=\"text/css\" href=\"http://c1.sainsburys.co.uk/wcsstore7.11.1.161/SainsburysStorefrontAssetStore/wcassets/groceries/css/espot.css\" rel=\"stylesheet\" media=\"all\" /> "
    	  		+ "<link type=\"text/css\" href=\"http://c1.sainsburys.co.uk/wcsstore7.11.1.161/SainsburysStorefrontAssetStore/css/print.css\" rel=\"stylesheet\" media=\"print\"/> <!-- END CommonCSSToInclude.jspf -->"
    	  		+ "<!-- BEGIN CommonJSToInclude.jspf --> <meta name=\"CommerceSearch\" content=\"storeId_10151\" /><div class=\"productSummary\"> <div class=\"productTitleDescriptionContainer\"> <h1>Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)</h1> </div> "
    	  		+ "<p class=\"pricePerUnit\"> &pound;1.50<abbr title=\"per\">/</abbr><abbr title=\"unit\"><span class=\"pricePerUnitUnit\">unit</span></abbr> </p></div></html>";
    	  
    	  Product expected = new Product("Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)","Buy Sainsbury's Apricot Ripe & Ready x5 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.",2.0f,1.50f);
    	  Product product = webScraperDAO.getProduct(HTML);
          assertThat(product.getTitle(),is(expected.getTitle()));
          assertThat(product.getDescription(),is(expected.getDescription()));
          assertThat(product.getPrice(),is(expected.getPrice()));
          assertThat(product.getSize(),is(2.0f));
    }
    //....
    
}
