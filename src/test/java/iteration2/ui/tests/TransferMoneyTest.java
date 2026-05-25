package iteration2.ui.tests;

import iteration2.ui.utils.UiTestData;
import models.CreateAccountResponse;
import models.CreateUserRequest;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static iteration2.ui.utils.UiAssertions.assertAlertContainsAndAccept;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferMoneyTest extends BaseUiTest {

    @Test
    public void userCanTransferMoneyWithValidAmountTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double transferAmount = UiTestData.randomTransferAmount();
        String transferAmountText = UiTestData.amountAsText(transferAmount);

        uiApiBridge.deposit(sender, senderAccount.getId(), transferAmount + 100.00);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoney(
                receiver.getUsername(),
                receiverAccount.getAccountNumber(),
                transferAmountText
        );

        assertAlertContainsAndAccept(ResponseSpecs.TRANSFER_SUCCESSFULLY);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer - transferAmount);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer + transferAmount);

        assertThat(senderAccountAfterTransfer.getTransactions())
                .isNotEmpty();

        assertThat(receiverAccountAfterTransfer.getTransactions())
                .isNotEmpty();

        assertThat(senderAccountAfterTransfer.getTransactions().toString())
                .contains("TRANSFER_OUT");

        assertThat(receiverAccountAfterTransfer.getTransactions().toString())
                .contains("TRANSFER_IN");
    }

    @Test
    public void userCanTransferMaximumAllowedAmountTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        uiApiBridge.deposit(sender, senderAccount.getId(), 10000.00);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoney(
                receiver.getUsername(),
                receiverAccount.getAccountNumber(),
                UiTestData.MAX_TRANSFER_AMOUNT
        );

        assertAlertContainsAndAccept(ResponseSpecs.TRANSFER_SUCCESSFULLY);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer - 10000.00);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer + 10000.00);

        assertThat(senderAccountAfterTransfer.getTransactions().toString())
                .contains("TRANSFER_OUT");

        assertThat(receiverAccountAfterTransfer.getTransactions().toString())
                .contains("TRANSFER_IN");
    }

    @Test
    public void userCanNotTransferAmountGreaterThanMaximumAllowedAmountTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        uiApiBridge.deposit(sender, senderAccount.getId(), 10000.00);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoney(
                receiver.getUsername(),
                receiverAccount.getAccountNumber(),
                UiTestData.GREATER_THAN_MAX_TRANSFER_AMOUNT
        );

        assertAlertContainsAndAccept(ResponseSpecs.INVALID_TRANSFER_MESSAGE);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer);
    }

    @Test
    public void userCanNotTransferAmountGreaterThanSenderAccountBalanceTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderInitialBalance = 100.00;
        double transferAmount = 200.00;

        uiApiBridge.deposit(sender, senderAccount.getId(), senderInitialBalance);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoney(
                receiver.getUsername(),
                receiverAccount.getAccountNumber(),
                UiTestData.amountAsText(transferAmount)
        );

        assertAlertContainsAndAccept(ResponseSpecs.INVALID_TRANSFER_MESSAGE);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer);
    }

    @Test
    public void userCanNotSubmitTransferWithoutSelectedSenderAccountTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double transferAmount = UiTestData.randomTransferAmount();
        String transferAmountText = UiTestData.amountAsText(transferAmount);

        uiApiBridge.deposit(sender, senderAccount.getId(), transferAmount + 100.00);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoneyWithoutSelectedSenderAccount(
                receiver.getUsername(),
                receiverAccount.getAccountNumber(),
                transferAmountText
        );

        assertAlertContainsAndAccept(ResponseSpecs.TRANSFER_REQUIRED_FIELDS_MESSAGE);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer);
    }

    @Test
    public void userCanNotSubmitTransferWithoutRecipientAccountNumberTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double transferAmount = UiTestData.randomTransferAmount();
        String transferAmountText = UiTestData.amountAsText(transferAmount);

        uiApiBridge.deposit(sender, senderAccount.getId(), transferAmount + 100.00);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoneyWithoutRecipientAccountNumber(
                receiver.getUsername(),
                transferAmountText
        );

        assertAlertContainsAndAccept(ResponseSpecs.TRANSFER_REQUIRED_FIELDS_MESSAGE);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer);
    }

    @Test
    public void userCanNotSubmitTransferWithoutConfirmationCheckboxTest() {
        CreateUserRequest sender = uiApiBridge.createUser();
        CreateUserRequest receiver = uiApiBridge.createUser();

        uiApiBridge.createAccount(sender);
        uiApiBridge.createAccount(receiver);

        CreateAccountResponse senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double transferAmount = UiTestData.randomTransferAmount();
        String transferAmountText = UiTestData.amountAsText(transferAmount);

        uiApiBridge.deposit(sender, senderAccount.getId(), transferAmount + 100.00);

        senderAccount = uiApiBridge.getUserAccounts(sender)[0];
        receiverAccount = uiApiBridge.getUserAccounts(receiver)[0];

        double senderBalanceBeforeTransfer = senderAccount.getBalance();
        double receiverBalanceBeforeTransfer = receiverAccount.getBalance();

        loginAsUserByToken(sender);

        customerUiSteps.openTransferPage();

        customerUiSteps.transferMoneyWithoutConfirmation(
                receiver.getUsername(),
                receiverAccount.getAccountNumber(),
                transferAmountText
        );

        assertAlertContainsAndAccept(ResponseSpecs.TRANSFER_REQUIRED_FIELDS_MESSAGE);

        CreateAccountResponse senderAccountAfterTransfer = uiApiBridge.getUserAccounts(sender)[0];
        CreateAccountResponse receiverAccountAfterTransfer = uiApiBridge.getUserAccounts(receiver)[0];

        assertThat(senderAccountAfterTransfer.getBalance())
                .isEqualTo(senderBalanceBeforeTransfer);

        assertThat(receiverAccountAfterTransfer.getBalance())
                .isEqualTo(receiverBalanceBeforeTransfer);
    }
}