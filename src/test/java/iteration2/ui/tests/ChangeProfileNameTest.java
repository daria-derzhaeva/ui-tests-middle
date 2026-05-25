package iteration2.ui.tests;

import generators.RandomData;
import models.CreateUserRequest;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static iteration2.ui.utils.UiAssertions.assertAlertContainsAndAccept;
import static org.assertj.core.api.Assertions.assertThat;

public class ChangeProfileNameTest extends BaseUiTest {

    @Test
    public void userCanChangeProfileNameToValidNameTest() {
        CreateUserRequest user = uiApiBridge.createUser();
        String expectedName = RandomData.getValidName();

        loginAsUserByToken(user);

        customerUiSteps.openEditProfilePage();
        customerUiSteps.changeProfileName(expectedName);

        assertAlertContainsAndAccept(ResponseSpecs.PROFILE_UPDATED_UI_MESSAGE);

        assertThat(uiApiBridge.getCustomerProfile(user).getName())
                .isEqualTo(expectedName);
    }

    @Test
    public void userCanNotChangeProfileNameWithInvalidFormatTest() {
        CreateUserRequest user = uiApiBridge.createUser();
        String nameBeforeUpdate = uiApiBridge.getCustomerProfile(user).getName();

        loginAsUserByToken(user);

        customerUiSteps.openEditProfilePage();
        customerUiSteps.changeProfileName("John Smith1");

        assertAlertContainsAndAccept(ResponseSpecs.INVALID_PROFILE_NAME_MESSAGE);

        assertThat(uiApiBridge.getCustomerProfile(user).getName())
                .isEqualTo(nameBeforeUpdate);
    }
}