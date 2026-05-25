package iteration2.ui.tests;

import iteration2.ui.steps.AdminUiSteps;
import iteration2.ui.steps.CustomerUiSteps;
import iteration2.ui.utils.UiApiBridge;
import iteration2.ui.utils.UiConfig;
import models.CreateUserRequest;
import org.junit.jupiter.api.BeforeAll;

public class BaseUiTest {

    protected final UiApiBridge uiApiBridge = new UiApiBridge();
    protected final CustomerUiSteps customerUiSteps = new CustomerUiSteps();
    protected final AdminUiSteps adminUiSteps = new AdminUiSteps();

    @BeforeAll
    public static void setupUi() {
        UiConfig.setupBrowser();
    }

    protected void loginAsUser(CreateUserRequest user) {
        customerUiSteps.loginAsUser(user.getUsername(), user.getPassword());
    }

    protected void loginAsUserByToken(CreateUserRequest user) {
        customerUiSteps.loginAsUserByToken(uiApiBridge.getUserToken(user));
    }

    protected void loginAsAdmin() {
        adminUiSteps.loginAsAdmin();
    }
}