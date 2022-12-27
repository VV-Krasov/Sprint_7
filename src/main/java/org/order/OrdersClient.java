package org.order;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    protected final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    protected final String ROOT = "/api/v1/orders";

    public ValidatableResponse create(Order order)
    {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT)
                .then();
    }

    public ValidatableResponse getAll()
    {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(ROOT)
                .when()
                .get()
                .then();
    }
}
