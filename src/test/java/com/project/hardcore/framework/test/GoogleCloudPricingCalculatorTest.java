package com.project.hardcore.framework.test;

import com.project.hardcore.framework.model.Instance;
import com.project.hardcore.framework.page.ComputeEnginePage;
import com.project.hardcore.framework.page.MainPage;
import com.project.hardcore.framework.page.YopmailEmailPage;
import com.project.hardcore.framework.service.InstanceCreator;
import com.project.hardcore.framework.util.TabUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleCloudPricingCalculatorTest extends CommonConditions{

    @Test
    public void testGoogleCloudPricingCalculator() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage()
                .openComputeEnginePage();
        TabUtils.switchToFirstTab(driver);

        Instance instance = InstanceCreator.withCredentialsFromProperty();
        ComputeEnginePage computeEnginePage = new ComputeEnginePage(driver);
        computeEnginePage.openPage()
                .fillComputeEngineForm(instance);
        computeEnginePage.emailEstimate();

        TabUtils.switchToNewTab(driver);
        YopmailEmailPage yopmailEmailPage = new YopmailEmailPage(driver);
        yopmailEmailPage.openPage()
                .getYopmailEmailCopy();

        TabUtils.switchToFirstTab(driver);
        computeEnginePage.sendEmail(yopmailEmailPage);

        String actualCost = computeEnginePage.getTotalEstimatedCost()
                .getText()
                .trim()
                .substring(26);

        Assert.assertEquals(actualCost, computeEnginePage.getEstimatedComponentCost(), "ERROR");
    }
}