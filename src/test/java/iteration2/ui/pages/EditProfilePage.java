package iteration2.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

public class EditProfilePage {

    private static final String PROFILE_NAME_INPUT = "input[placeholder='Enter new name']";

    public void open() {
        Selenide.open("/edit-profile");
    }

    public void shouldBeOpened() {
        $(Selectors.withText("Edit Profile"))
                .shouldBe(Condition.visible);

        $(PROFILE_NAME_INPUT)
                .shouldBe(Condition.visible);
    }

    public void setProfileName(String name) {
        $(PROFILE_NAME_INPUT)
                .shouldBe(Condition.visible)
                .setValue(name);
    }

    public void submitProfileName() {
        $(Selectors.withText("Save Changes"))
                .shouldBe(Condition.visible)
                .click();
    }
}