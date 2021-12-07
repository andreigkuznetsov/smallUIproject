package yahoo.andreikuzn.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import configuration.ConfigurationSelenoidSets;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import yahoo.andreikuzn.pages.AboutCompanyPage;

import static java.lang.String.format;

public class TestBase {

    AboutCompanyPage aboutCompanyPage = new AboutCompanyPage();

    @BeforeAll
    static void setup() {
        ConfigurationSelenoidSets cfg = ConfigFactory.create(ConfigurationSelenoidSets.class);
        String login = cfg.login();
        String password = cfg.password();

        //SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        //Configuration.startMaximized = true;
        Configuration.remote = format("https://%s:%s@selenoid.autotests.cloud/wd/hub", login, password);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

}
