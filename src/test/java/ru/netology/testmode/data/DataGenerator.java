package ru.netology.testmode.data;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
  private static final RequestSpecification requestSpec = new RequestSpecBuilder()
          .setBaseUri("http://localhost")
          .setPort(9999)
          .setAccept(ContentType.JSON)
          .setContentType(ContentType.JSON)
          .log(LogDetail.ALL)
          .build();
  private static final Faker faker = new Faker(new Locale("en"));

  private DataGenerator() {
  }

  public static void sendRequest(RegistrationDto user) {
    given()                                    // "дано"
            .spec(requestSpec)                 // указываем, какую спецификацию используем
            .body(user)                        // .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
            .when()                            // "когда"
            .post("/api/system/users")    // на какой путь, относительно BaseUri отправляем запрос
            .then()                            // "тогда ожидаем"
            .statusCode(200); // код 200 OK
  }

  public static String getRandomLogin() {
    return faker.name().firstName();
  }

  public static String getRandomPassword() {
    return faker.internet().password();
  }

  public static class Registration {
    private Registration() {
    }

    public static RegistrationDto getRegisteredUser(String status) {
      RegistrationDto registeredUser = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
      sendRequest(registeredUser);
      return registeredUser;
    }

    public static RegistrationDto getRegisteredUserWithIncorrectPassword(String status) {
      String incorrectPassword = "password";
      RegistrationDto registeredUser = new RegistrationDto(getRandomLogin(), incorrectPassword, status);
      sendRequest(registeredUser);
      return new RegistrationDto(getRandomLogin(), incorrectPassword, status);
    }

    public static RegistrationDto getRegisteredUserWithIncorrectLogin(String status) {
      String incorrectLogin = "Вася";
      RegistrationDto registeredUser = new RegistrationDto(incorrectLogin, getRandomPassword(), status);
      sendRequest(registeredUser);
      return new RegistrationDto(incorrectLogin, getRandomPassword(), status);
    }

    public static RegistrationDto getRegisteredUserWithIncorrectLoginAndPassword(String status) {
      String incorrectPassword = "password";
      String incorrectLogin = "Вася";
      RegistrationDto registeredUser = new RegistrationDto(incorrectLogin, incorrectPassword, status);
      sendRequest(registeredUser);
      return new RegistrationDto(incorrectLogin, incorrectPassword, status);
    }

    public static RegistrationDto getBlockedUserWithIncorrectLoginAndPassword(String status) {
      String incorrectPassword = "password";
      String incorrectLogin = "Вася";
      RegistrationDto registeredUser = new RegistrationDto(incorrectLogin, incorrectPassword, status);
      sendRequest(registeredUser);
      return new RegistrationDto(incorrectLogin, incorrectPassword, status);
    }

    public static RegistrationDto getEmptyLoginRegisteredUser(String status) {
      String emptyLogin = "";
      RegistrationDto registeredUser = new RegistrationDto(emptyLogin, getRandomPassword(), status);
      sendRequest(registeredUser);
      return new RegistrationDto(emptyLogin, getRandomPassword(), status);
    }

    public static RegistrationDto getEmptyPasswordRegisteredUser(String status) {
      String emptyPassword = "";
      RegistrationDto registeredUser = new RegistrationDto(getRandomLogin(), emptyPassword, status);
      sendRequest(registeredUser);
      return new RegistrationDto(getRandomLogin(), emptyPassword, status);
    }

    public static RegistrationDto getEmptyLoginAndPasswordRegisteredUser(String status) {
      String emptyPassword = "";
      String emptyLogin = "";
      RegistrationDto registeredUser = new RegistrationDto(emptyLogin, emptyPassword, status);
      sendRequest(registeredUser);
      return new RegistrationDto(emptyLogin, emptyPassword, status);
    }
  }

  @Value
  public static class RegistrationUser {
    String login;
    String password;
    String status;
  }
}