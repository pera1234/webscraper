package com.hss.scraper;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * 
 * @author harneksandhar
 *
 */
@CucumberOptions(
        glue = {"com.hss"},
        features = {"src/test/java/com/hss/scraper"},
        strict = true, format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumberimpl.json"})
public class RunCukeTest extends AbstractTestNGCucumberTests {
}
