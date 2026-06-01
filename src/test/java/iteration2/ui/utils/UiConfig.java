package iteration2.ui.utils;

import com.codeborne.selenide.Configuration;
import configs.Config;

public class UiConfig {

    private UiConfig() {
    }

    public static void setupBrowser() {
        String uiUrl = Config.getProperty("uiUrl");

        if (uiUrl == null) {
            throw new RuntimeException("uiUrl is not found in config.properties");
        }

        Configuration.baseUrl = uiUrl;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;

        Configuration.reportsFolder = "build/reports/tests";
        Configuration.screenshots = true;
        Configuration.savePageSource = true;
    }
}