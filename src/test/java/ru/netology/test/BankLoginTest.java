package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.DbUtils;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankLoginTest {
    @AfterAll
    static void cleanDb() {
        DbUtils.cleanDatabase();
    }

    @Test
    void shouldLoginUsingCodeFromDb() {
        var authInfo = DataHelper.getValidAuthInfo();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(authInfo);


        var code = DbUtils.getAuthCode(authInfo.getLogin());
        verificationPage.validVerify(code);
    }

    @Test
    void shouldBlockUserAfterThreeInvalidPasswordAttempts() {
        var invalidAuth = DataHelper.getInvalidAuthInfo();
        var loginPage = open("http://localhost:9999", LoginPage.class);

        for (int i = 0; i < 3; i++) {
            loginPage.invalidLogin(invalidAuth);
        }

        var status = DbUtils.getUserStatus(invalidAuth.getLogin());
        assertEquals("blocked", status);
    }
}
