package com.example.sprint_7_mandrikov_ea;

import io.restassured.response.Response;
import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderController {
    public static Response executeOrder(String path) {
        File json = new File(path);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        return response;
    }

    public static Response executeGetOrderByTrackId(int trackId) {
        return given()
                .header("Content-type", "application/json")
                .queryParam("t",trackId)
                .when()
                .get("/api/v1/orders/track");
    }
    public static int parseOrderIdFromResponse(Response response) {
        int trackId = response.jsonPath().get("track");
        Response responseOrder = executeGetOrderByTrackId(trackId);
        return responseOrder.jsonPath().get("order.id");
    }

    public static Response executeAcceptOrder(int courierId, int orderId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .queryParam("courierId", courierId)
                        .when()
                        .put(String.format("/api/v1/orders/accept/%s",orderId));
        return response;
    }

    public static Response executeListOrder(int courierId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .queryParam("courierId", courierId)
                        .when()
                        .get("/api/v1/orders");
        return response;
    }

    public static Response executeListOrder(Map<String,String> queryParams) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .queryParams(queryParams)
                        .when()
                        .get("/api/v1/orders");
        return response;
    }
}

