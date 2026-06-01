package iteration2.ui.utils;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class UiTestData {

    public static final String MAX_DEPOSIT_AMOUNT = "5000.00";
    public static final String GREATER_THAN_MAX_DEPOSIT_AMOUNT = "5000.01";

    public static final String MAX_TRANSFER_AMOUNT = "10000.00";
    public static final String GREATER_THAN_MAX_TRANSFER_AMOUNT = "10000.01";

    public static final double MAX_DEPOSIT_AMOUNT_VALUE = 5000.00;
    public static final double MAX_TRANSFER_AMOUNT_VALUE = 10000.00;

    public static final double BALANCE_RESERVE_AMOUNT = 100.00;
    public static final double SENDER_INITIAL_BALANCE = 100.00;
    public static final double TRANSFER_AMOUNT_GREATER_THAN_BALANCE = 200.00;

    private UiTestData() {
    }

    public static double randomDepositAmount() {
        return ThreadLocalRandom.current().nextInt(1, 1000);
    }

    public static double randomTransferAmount() {
        return ThreadLocalRandom.current().nextInt(1, 500);
    }

    public static String amountAsText(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }
}