package org.qz.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/manage_contacts.feature",
        glue = {"org.qz.steps","org.qz.hooks"},
        tags = "@ManageContacts",
        monochrome = true,
        plugin = {"pretty","com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class MobileRunner extends AbstractTestNGCucumberTests {

}
