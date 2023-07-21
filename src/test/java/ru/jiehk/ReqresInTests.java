package ru.jiehk;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTests extends TestBase{

    @Test
    void registrationTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    void missingPasswordRegistrationTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"\" }";
        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingEmailRegistrationTest() {
        String data = "{ \"email\": \"\", \"password\": \"12345\" }";
        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void usersListPaginationTest() {
        given()
                .log().all()
                .param("page", 1)
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", hasSize(6))
                .body("per_page", is(6));
    }

    @Test
    void emptyUsersListTest() {
        given()
                .log().all()
                .param("page", 100)
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("data", empty());
    }

}
