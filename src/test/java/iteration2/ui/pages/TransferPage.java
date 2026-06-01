package iteration2.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private static final String ACCOUNT_SELECT = "select";
    private static final String RECIPIENT_NAME_INPUT = "input[placeholder='Enter recipient name']";
    private static final String RECIPIENT_ACCOUNT_INPUT = "input[placeholder='Enter recipient account number']";
    private static final String AMOUNT_INPUT = "input[placeholder='Enter amount']";
    private static final String CONFIRM_TRANSFER_CHECKBOX = "input[type='checkbox']";

    public void shouldBeOpened() {
        $(RECIPIENT_NAME_INPUT)
                .shouldBe(Condition.visible);
    }

    public void selectFirstSenderAccount() {
        $(ACCOUNT_SELECT)
                .shouldBe(Condition.visible)
                .selectOption(1);
    }

    public void setRecipientName(String recipientName) {
        $(RECIPIENT_NAME_INPUT)
                .shouldBe(Condition.visible)
                .setValue(recipientName);
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        $(RECIPIENT_ACCOUNT_INPUT)
                .shouldBe(Condition.visible)
                .setValue(recipientAccountNumber);
    }

    public void setAmount(String amount) {
        $(AMOUNT_INPUT)
                .shouldBe(Condition.visible)
                .setValue(amount);
    }

    public void confirmTransfer() {
        $(CONFIRM_TRANSFER_CHECKBOX)
                .shouldBe(Condition.visible)
                .click();
    }

    public void submitTransfer() {
        $(Selectors.withText("Send Transfer"))
                .shouldBe(Condition.visible)
                .click();
    }
}