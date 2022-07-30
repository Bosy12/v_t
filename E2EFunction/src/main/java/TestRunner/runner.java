package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@io.cucumber.junit.CucumberOptions(
		features = "Features", 
		glue ={"E2EFunction"},
	
		plugin = { "pretty", "html:target/cucumber-reports" },
		monochrome = true
	)
public class runner {

}
