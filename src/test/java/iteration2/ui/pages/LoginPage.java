package iteration2.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private static final String USERNAME_INPUT = "input[placeholder='Username']";
    private static final String PASSWORD_INPUT = "input[placeholder='Password']";

    public void open() {
        Selenide.open("/login");
    }

    public void shouldBeOpened() {
        $(USERNAME_INPUT)
                .shouldBe(Condition.visible);
    }

    public void setUsername(String username) {
        $(USERNAME_INPUT)
                .shouldBe(Condition.visible)
                .setValue(username);
    }

    public void setPassword(String password) {
        $(PASSWORD_INPUT)
                .shouldBe(Condition.visible)
                .setValue(password);
    }

    public void submitLogin() {
        $("button")
                .shouldBe(Condition.visible)
                .click();
    }
}