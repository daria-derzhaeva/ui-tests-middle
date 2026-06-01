package iteration2.ui.pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DepositPage {

    private static final String ACCOUNT_SELECT = "select";
    private static final String AMOUNT_INPUT = "input[placeholder='Enter amount']";

    public void shouldBeOpened() {
        $(AMOUNT_INPUT)
                .shouldBe(Condition.visible);
    }

    public void selectFirstAccount() {
        $(ACCOUNT_SELECT)
                .shouldBe(Condition.visible)
                .selectOption(1);
    }

    public void setAmount(String amount) {
        $(AMOUNT_INPUT)
                .shouldBe(Condition.visible)
                .setValue(amount);
    }

    public void submitDeposit() {
        $$("button")
                .filterBy(Condition.text("Deposit"))
                .last()
                .shouldBe(Condition.visible)
                .click();
    }
}