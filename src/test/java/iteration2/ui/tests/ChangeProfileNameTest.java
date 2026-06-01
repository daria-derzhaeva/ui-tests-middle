package iteration2.ui.tests;

import generators.RandomData;
import iteration2.ui.pages.EditProfilePage;
import models.CreateUserRequest;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static iteration2.ui.utils.UiAssertions.assertAlertContainsAndAccept;
import static org.assertj.core.api.Assertions.assertThat;

public class ChangeProfileNameTest extends BaseUiTest {

    private final EditProfilePage editProfilePage = new EditProfilePage();

    @Test
    public void userCanChangeProfileNameToValidNameTest() {
        CreateUserRequest user = uiApiBridge.createUser();
        String expectedName = RandomData.getValidName();

        loginAsUserByToken(user);

        editProfilePage
                .open()
                .shouldBeOpened()
                .setProfileName(expectedName)
                .submitProfileName();

        assertAlertContainsAndAccept(ResponseSpecs.PROFILE_UPDATED_UI_MESSAGE);

        assertThat(uiApiBridge.getCustomerProfile(user).getName())
                .isEqualTo(expectedName);
    }

    @Test
    public void userCanNotChangeProfileNameWithInvalidFormatTest() {
        CreateUserRequest user = uiApiBridge.createUser();
        String invalidName = RandomData.getInvalidName();
        String nameBeforeUpdate = uiApiBridge.getCustomerProfile(user).getName();

        loginAsUserByToken(user);

        editProfilePage
                .open()
                .shouldBeOpened()
                .setProfileName(invalidName)
                .submitProfileName();

        assertAlertContainsAndAccept(ResponseSpecs.INVALID_PROFILE_NAME_MESSAGE);

        assertThat(uiApiBridge.getCustomerProfile(user).getName())
                .isEqualTo(nameBeforeUpdate);
    }
}