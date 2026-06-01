package generators;

import configs.Config;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomData {

    private static final Random RANDOM = new Random();

    private static final List<String> FIRST_NAMES = List.of(
            "Darya", "Anna", "Maria", "Sofia", "Alina", "Elena"
    );

    private static final List<String> LAST_NAMES = List.of(
            "Ivanova", "Petrova", "Smirnova", "Sidorova", "Volkova"
    );

    private RandomData() {
    }

    public static String getUsername() {
        return "user_" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, GeneratingRule.USERNAME_RANDOM_LENGTH);
    }

    public static String getPassword() {
        return Config.getDefaultPassword();
    }

    public static String getValidName() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()))
                + " "
                + LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()));
    }

    public static String getInvalidName() {
        return getValidName() + getRandomDigit();
    }

    private static int getRandomDigit() {
        return RANDOM.nextInt(10);
    }
}