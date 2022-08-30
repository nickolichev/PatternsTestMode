package ru.netology.testmode.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static ru.netology.testmode.data.DataGenerator.Registration.*;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

public class AccountTest {

  @BeforeEach
  public void setUp() {
    open("http://localhost:9999/");
  }

  @Test
  void correctLoginAndPasswordActiveUser() {
    RegistrationDto registeredUser = getRegisteredUser("active");
    $x("//input[@name='login']").setValue(registeredUser.getLogin());
    $x("//input[@name='password']").setValue(registeredUser.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//*[@class='heading heading_size_l heading_theme_alfa-on-white']")
            .should(visible, Duration.ofSeconds(5));
    $x("//*[@class='heading heading_size_l heading_theme_alfa-on-white']")
            .should(text("Личный кабинет"));
  }

  @Test
  void correctLoginAndPasswordBlockedUser() {
    RegistrationDto registeredUser = getRegisteredUser("blocked");
    $x("//input[@name='login']").setValue(registeredUser.getLogin());
    $x("//input[@name='password']").setValue(registeredUser.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//div[@data-test-id='error-notification']")
            .should(visible, Duration.ofSeconds(5));
    $x("//div[@data-test-id='error-notification']")
            .should(text("Ошибка! Пользователь заблокирован"));
  }

  @Test
  void incorrectPasswordRegisteredUser() {
    RegistrationDto registeredUser = getRegisteredUser("active");
    $x("//input[@name='login']").setValue(registeredUser.getLogin());
    $x("//input[@name='password']").setValue(getRandomPassword());
    $x("//*[@class='button__text']").click();
    $x("//div[@data-test-id='error-notification']")
            .should(visible, Duration.ofSeconds(5));
    $x("//div[@data-test-id='error-notification']")
            .should(text("Ошибка! Неверно указан логин или пароль"));
  }

  @Test
  void incorrectLoginRegisteredUser() {
    RegistrationDto registeredUser = getRegisteredUser("active");
    $x("//input[@name='login']").setValue(getRandomLogin());
    $x("//input[@name='password']").setValue(registeredUser.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//div[@data-test-id='error-notification']")
            .should(visible, Duration.ofSeconds(15));
    $x("//div[@data-test-id='error-notification']")
            .should(text("Ошибка! Неверно указан логин или пароль"));
  }

  @Test
  void incorrectLoginAndPasswordNotRegisteredUser() {
    RegistrationDto user = getUser("active");
    $x("//input[@name='login']").setValue(user.getLogin());
    $x("//input[@name='password']").setValue(user.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//div[@data-test-id='error-notification']")
            .should(text("Ошибка! Неверно указан логин или пароль"));
  }

  @Test
  void emptyFieldLoginRegisteredUser() {
    RegistrationDto registeredUser = getEmptyLoginRegisteredUser("active");
    $x("//input[@name='login']").setValue(registeredUser.getLogin());
    $x("//input[@name='password']").setValue(registeredUser.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void emptyFieldPasswordRegisteredUser() {
    RegistrationDto registeredUser = getEmptyPasswordRegisteredUser("active");
    $x("//input[@name='login']").setValue(registeredUser.getLogin());
    $x("//input[@name='password']").setValue(registeredUser.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void emptyFieldsLoginAndPasswordRegisteredUser() {
    RegistrationDto registeredUser = getEmptyLoginAndPasswordRegisteredUser("active");
    $x("//input[@name='login']").setValue(registeredUser.getLogin());
    $x("//input[@name='password']").setValue(registeredUser.getPassword());
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }
}

