package iteration2.ui.tests;

import iteration2.ui.utils.UiTestData;
import models.CreateAccountResponse;
import models.CreateUserRequest;
import models.TransactionType;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static iteration2.ui.utils.UiAssertions.assertAlertContainsAndAccept;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositMoneyTest extends BaseUiTest {

    @Test
    public void userCanDepositMoneyToOwnAccountTest() {
        CreateUserRequest user = uiApiBridge.createUser();

        uiApiBridge.createAccount(user);

        CreateAccountResponse[] accountsBeforeDeposit = uiApiBridge.getUserAccounts(user);
        assertThat(accountsBeforeDeposit).hasSize(1);

        double depositAmount = UiTestData.randomDepositAmount();
        String depositAmountText = UiTestData.amountAsText(depositAmount);

        double balanceBeforeDeposit = accountsBeforeDeposit[0].getBalance();

        loginAsUserByToken(user);

        customerUiSteps.openDepositPage();
        customerUiSteps.depositMoney(depositAmountText);

        assertAlertContainsAndAccept(ResponseSpecs.DEPOSIT_SUCCESSFULLY);

        CreateAccountResponse[] accountsAfterDeposit = uiApiBridge.getUserAccounts(user);

        assertThat(accountsAfterDeposit[0].getBalance())
                .isEqualTo(balanceBeforeDeposit + depositAmount);

        assertThat(accountsAfterDeposit[0].getTransactions())
                .isNotEmpty();

        assertThat(accountsAfterDeposit[0].getTransactions().toString())
                .contains(TransactionType.DEPOSIT.name());
    }

    @Test
    public void userCanDepositMaximumAllowedAmountTest() {
        CreateUserRequest user = uiApiBridge.createUser();

        uiApiBridge.createAccount(user);

        CreateAccountResponse[] accountsBeforeDeposit = uiApiBridge.getUserAccounts(user);
        assertThat(accountsBeforeDeposit).hasSize(1);

        double balanceBeforeDeposit = accountsBeforeDeposit[0].getBalance();

        loginAsUserByToken(user);

        customerUiSteps.openDepositPage();
        customerUiSteps.depositMoney(UiTestData.MAX_DEPOSIT_AMOUNT);

        assertAlertContainsAndAccept(ResponseSpecs.DEPOSIT_SUCCESSFULLY);

        CreateAccountResponse[] accountsAfterDeposit = uiApiBridge.getUserAccounts(user);

        assertThat(accountsAfterDeposit[0].getBalance())
                .isEqualTo(balanceBeforeDeposit + UiTestData.MAX_DEPOSIT_AMOUNT_VALUE);

        assertThat(accountsAfterDeposit[0].getTransactions())
                .isNotEmpty();

        assertThat(accountsAfterDeposit[0].getTransactions().toString())
                .contains(TransactionType.DEPOSIT.name());

    }

    @Test
    public void userCanNotDepositAmountGreaterThanMaximumAllowedAmountTest() {
        CreateUserRequest user = uiApiBridge.createUser();

        uiApiBridge.createAccount(user);

        CreateAccountResponse[] accountsBeforeDeposit = uiApiBridge.getUserAccounts(user);
        assertThat(accountsBeforeDeposit).hasSize(1);

        double balanceBeforeDeposit = accountsBeforeDeposit[0].getBalance();

        loginAsUserByToken(user);

        customerUiSteps.openDepositPage();
        customerUiSteps.depositMoney(UiTestData.GREATER_THAN_MAX_DEPOSIT_AMOUNT);

        assertAlertContainsAndAccept(ResponseSpecs.INVALID_DEPOSIT_AMOUNT_MESSAGE);

        CreateAccountResponse[] accountsAfterDeposit = uiApiBridge.getUserAccounts(user);

        assertThat(accountsAfterDeposit[0].getBalance())
                .isEqualTo(balanceBeforeDeposit);
    }

    @Test
    public void userCanNotSubmitDepositWithoutSelectedAccountTest() {
        CreateUserRequest user = uiApiBridge.createUser();

        uiApiBridge.createAccount(user);

        CreateAccountResponse[] accountsBeforeDeposit = uiApiBridge.getUserAccounts(user);
        assertThat(accountsBeforeDeposit).hasSize(1);

        double depositAmount = UiTestData.randomDepositAmount();
        String depositAmountText = UiTestData.amountAsText(depositAmount);

        double balanceBeforeDeposit = accountsBeforeDeposit[0].getBalance();

        loginAsUserByToken(user);

        customerUiSteps.openDepositPage();
        customerUiSteps.depositMoneyWithoutSelectedAccount(depositAmountText);

        assertAlertContainsAndAccept(ResponseSpecs.ACCOUNT_NOT_SELECTED_MESSAGE);

        CreateAccountResponse[] accountsAfterDeposit = uiApiBridge.getUserAccounts(user);

        assertThat(accountsAfterDeposit[0].getBalance())
                .isEqualTo(balanceBeforeDeposit);
    }

    @Test
    public void userCanNotSubmitDepositWithBlankAmountTest() {
        CreateUserRequest user = uiApiBridge.createUser();

        uiApiBridge.createAccount(user);

        CreateAccountResponse[] accountsBeforeDeposit = uiApiBridge.getUserAccounts(user);
        assertThat(accountsBeforeDeposit).hasSize(1);

        double balanceBeforeDeposit = accountsBeforeDeposit[0].getBalance();

        loginAsUserByToken(user);

        customerUiSteps.openDepositPage();
        customerUiSteps.submitDepositWithoutAmount();

        assertAlertContainsAndAccept(ResponseSpecs.INVALID_AMOUNT_MESSAGE);

        CreateAccountResponse[] accountsAfterDeposit = uiApiBridge.getUserAccounts(user);

        assertThat(accountsAfterDeposit[0].getBalance())
                .isEqualTo(balanceBeforeDeposit);
    }
}