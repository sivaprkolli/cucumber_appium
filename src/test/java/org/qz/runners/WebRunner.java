package org.qz.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/login.feature",
        glue = {"org.qz.steps","org.qz.hooks"},
        tags = "@LT3"
)
public class WebRunner extends AbstractTestNGCucumberTests {

}
