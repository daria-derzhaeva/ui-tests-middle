package iteration2.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

public class EditProfilePage {

    private static final String PROFILE_NAME_INPUT = "input[placeholder='Enter new name']";

    public EditProfilePage open() {
        Selenide.open("/edit-profile");
        return this;
    }

    public EditProfilePage shouldBeOpened() {
        $(Selectors.withText("Edit Profile"))
                .shouldBe(Condition.visible);

        $(PROFILE_NAME_INPUT)
                .shouldBe(Condition.visible);

        return this;
    }

    public EditProfilePage setProfileName(String name) {
        $(PROFILE_NAME_INPUT)
                .shouldBe(Condition.visible)
                .setValue(name);

        return this;
    }

    public EditProfilePage submitProfileName() {
        $(Selectors.withText("Save Changes"))
                .shouldBe(Condition.visible)
                .click();

        return this;
    }
}