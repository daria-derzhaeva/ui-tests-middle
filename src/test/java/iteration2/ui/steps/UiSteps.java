package iteration2.ui.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import iteration2.ui.utils.UiLocators;

import static com.codeborne.selenide.Selenide.*;

public class UiSteps {

    public void loginByUi(String username, String password) {
        Selenide.open("/login");

        $(UiLocators.USERNAME_INPUT)
                .shouldBe(Condition.visible)
                .setValue(username);

        $(UiLocators.PASSWORD_INPUT)
                .shouldBe(Condition.visible)
                .setValue(password);

        $("button")
                .shouldBe(Condition.visible)
                .click();

        assertUserDashboardOpened();
    }

    public void loginByLocalStorage(String authToken) {
        Selenide.open("/");

        executeJavaScript(
                "localStorage.setItem('authToken', arguments[0]);",
                authToken
        );

        Selenide.open("/dashboard");

        assertUserDashboardOpened();
    }

    public void assertUserDashboardOpened() {
        $(Selectors.byText("User Dashboard"))
                .shouldBe(Condition.visible);
    }

    public void openDepositPage() {
        $(Selectors.withText("Deposit Money"))
                .shouldBe(Condition.visible)
                .click();

        $(UiLocators.AMOUNT_INPUT)
                .shouldBe(Condition.visible);
    }

    public void submitDeposit(boolean selectAccount, String amount) {
        if (selectAccount) {
            $(UiLocators.ACCOUNT_SELECT)
                    .shouldBe(Condition.visible)
                    .selectOption(1);
        }

        if (amount != null) {
            $(UiLocators.AMOUNT_INPUT)
                    .shouldBe(Condition.visible)
                    .setValue(amount);
        }

        $$("button")
                .filterBy(Condition.text("Deposit"))
                .last()
                .shouldBe(Condition.visible)
                .click();
    }

    public void openTransferPage() {
        $(Selectors.withText("Make a Transfer"))
                .shouldBe(Condition.visible)
                .click();

        $(UiLocators.RECIPIENT_NAME_INPUT)
                .shouldBe(Condition.visible);
    }

    public void submitTransfer(
            boolean selectSenderAccount,
            String recipientName,
            String recipientAccountNumber,
            String amount,
            boolean confirm
    ) {
        if (selectSenderAccount) {
            $(UiLocators.ACCOUNT_SELECT)
                    .shouldBe(Condition.visible)
                    .selectOption(1);
        }

        if (recipientName != null) {
            $(UiLocators.RECIPIENT_NAME_INPUT)
                    .shouldBe(Condition.visible)
                    .setValue(recipientName);
        }

        if (recipientAccountNumber != null) {
            $(UiLocators.RECIPIENT_ACCOUNT_INPUT)
                    .shouldBe(Condition.visible)
                    .setValue(recipientAccountNumber);
        }

        if (amount != null) {
            $(UiLocators.AMOUNT_INPUT)
                    .shouldBe(Condition.visible)
                    .setValue(amount);
        }

        if (confirm) {
            $(UiLocators.CONFIRM_TRANSFER_CHECKBOX)
                    .shouldBe(Condition.visible)
                    .click();
        }

        $(Selectors.withText("Send Transfer"))
                .shouldBe(Condition.visible)
                .click();
    }

    public void openProfilePage() {
        Selenide.open("/edit-profile");

        $(Selectors.withText("Edit Profile"))
                .shouldBe(Condition.visible);

        $(UiLocators.PROFILE_NAME_INPUT)
                .shouldBe(Condition.visible);
    }

    public void submitProfileName(String name) {
        $(UiLocators.PROFILE_NAME_INPUT)
                .shouldBe(Condition.visible)
                .setValue(name);

        $(Selectors.withText("Save Changes"))
                .shouldBe(Condition.visible)
                .click();
    }
}