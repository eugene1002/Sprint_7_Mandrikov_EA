package com.example.sprint_7_mandrikov_ea;

import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CourierController {
    public static Response executeCreate(String path) {
        File json = new File(path);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }

    private static Response executeDelete(int courierId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(String.format("/api/v1/courier/%s", courierId));
        return response;
    }

    public static Response executeDelete(String path) {
        return executeDelete(getCourierId(path));
    }

    public static int getCourierId(String path) {
        Response response = executeLogin(path);
        int id = response.jsonPath().get("id");
        return id;
    }
    public static Response executeLogin(String path) {
        File json = new File(path);
        return
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
    }
}

