package org.courier;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    protected final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    protected final String ROOT = "/api/v1/courier";
    public ValidatableResponse createCourier(Courier courier)
    {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(ROOT)
                .then();
    }
    public ValidatableResponse login(CourierCredentials courierCredentials)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courierCredentials)
                .when()
                .post(ROOT + "/login")
                .then();
    }

    public ValidatableResponse delete(CourierId courierId)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courierId)
                .when()
                .delete(ROOT + "/" + courierId.getId())
                .then();
    }
}
