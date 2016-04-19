Feature: WEBSCRAPER

Scenario: Convert a DTO to JSON
  Given the following products
    |title  							 			|description   																																																|size |price |
    |Sainsbury's Apricot Ripe & Ready x5 			|Buy Sainsbury's Apricot Ripe & Ready x5 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.			|34.0 |3.50  |
    |Sainsbury's Avocado Ripe & Ready XL Loose 300g |Buy Sainsbury's Avocado Ripe & Ready XL Loose 300g online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points. |35.0 |1.50  |
  When the scrape process is called
  Then the Json is created
  