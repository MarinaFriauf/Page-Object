package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.page.DashboardPage.pushFirst;
import static ru.netology.page.DashboardPage.pushSecond;

public class UpTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferFromFirstCard() {
        val dashboardPage = new DashboardPage();
        val firstCardBalanceStart = dashboardPage.getFirstBalance();
        val secondCardBalanceStart = dashboardPage.getSecondBalance();
        int amount = 100;

        val transactionPage = pushSecond();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        val firstCardBalanceResult = firstCardBalanceStart - amount;
        val secondCardBalanceResult = secondCardBalanceStart + amount;

        assertEquals(firstCardBalanceResult, dashboardPage.getFirstBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondBalance());
    }

    @Test
    public void shouldTransferFromSecondCard() {
        val dashboardPage = new DashboardPage();
        val firstCardBalanceStart = dashboardPage.getFirstBalance();
        val secondCardBalanceStart = dashboardPage.getSecondBalance();
        int amount = 1000;

        val transactionPage = pushFirst();
        transactionPage.transferMoney(amount, getSecondCardNumber());
        val firstCardBalanceResult = firstCardBalanceStart + amount;
        val secondCardBalanceResult = secondCardBalanceStart - amount;

        assertEquals(firstCardBalanceResult, dashboardPage.getFirstBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondBalance());
    }

//    @Test
//    public void shouldGetError() {
//        int amount = 100_000;
//        val transactionPage = pushSecond();
//        transactionPage.transferMoney(amount, getFirstCardNumber());
//        transactionPage.getError();
//    }

}