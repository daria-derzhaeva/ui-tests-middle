package iteration2.ui.steps;

import models.CreateAccountResponse;
import models.CreateUserRequest;
import models.LoginUserRequest;
import org.apache.http.HttpStatus;
import requests.steps.AdminSteps;
import specs.RequestSpecs;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    private final AdminSteps adminSteps = new AdminSteps();

    public CreateUserRequest createUser() {
        return adminSteps.createUser();
    }

    public String getUserToken(CreateUserRequest user) {
        LoginUserRequest loginUserRequest = LoginUserRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        return adminSteps.loginUser(loginUserRequest);
    }

    public void createAccount(CreateUserRequest user) {
        given()
                .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                .post("http://localhost:4111/api/v1/accounts")
                .then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    public void deposit(CreateUserRequest user, long accountId, double amount) {
        given()
                .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                .contentType("application/json")
                .body(Map.of(
                        "id", accountId,
                        "balance", amount
                ))
                .post("http://localhost:4111/api/v1/accounts/deposit")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    public CreateAccountResponse[] getUserAccounts(CreateUserRequest user) {
        return given()
                .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(CreateAccountResponse[].class);
    }

    public String getProfileName(CreateUserRequest user) {
        return given()
                .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                .get("http://localhost:4111/api/v1/customer/profile")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("name");
    }
}