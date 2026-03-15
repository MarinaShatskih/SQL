package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getValidAuthInfo() {
        // Проверьте пароль: в этой задаче часто используется "pass" вместо "qwerty123"
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo("vasya", faker.internet().password());
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(faker.name().username(), faker.internet().password());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    // Этот класс ОБЯЗАТЕЛЬНО должен быть здесь, чтобы SQLHelper его видел
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        private String code;
    }
}
