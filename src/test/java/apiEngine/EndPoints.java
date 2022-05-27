package apiEngine;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import modals.requests.AddBookRequest;
import modals.requests.AuthRequest;
import modals.requests.RemoveBookRequest;

public class EndPoints {


    public EndPoints(String BASE_URL) {
        RestAssured.baseURI=BASE_URL;
    }

    public Response createUser(AuthRequest authRequest){
        RequestSpecification requestSpecification = RestAssured.given()
                .when()
                .header("Content-Type", "application/json");
        Response response = requestSpecification.body(authRequest).post("/Account/v1/User");
        return response;
    }

    public Response generateToken(AuthRequest authRequest){
        RequestSpecification requestSpecification1 = RestAssured.given()
                .when()
                .header("Content-Type", "application/json");
        Response generateTokenResponse = requestSpecification1.body(authRequest).post("/Account/v1/GenerateToken");
        return generateTokenResponse;
    }

    public Response getBooksFromList(String token){
        RequestSpecification requestSpecification = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token);
        Response response = requestSpecification.get("/BookStore/v1/Books");
        return response;
    }
    public Response addBooks(AddBookRequest addBookRequest, String token){
        RequestSpecification requestSpecification = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token);
        Response response = requestSpecification.body(addBookRequest).post("/BookStore/v1/Books");
        return response;
    }
    public Response getAddedBook(String token,String isbn){
        RequestSpecification requestSpecification = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token);
        Response response = requestSpecification.get("/BookStore/v1/Book?ISBN=" + isbn);
        return response;
    }
    public Response removeBook(RemoveBookRequest removeBookRequest,String token){
        RequestSpecification requestSpecification = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token);
        Response response = requestSpecification.body(removeBookRequest).delete("/BookStore/v1/Book");
        return response;
    }
    public Response verifyRemovedBook(String userId,String token){
        RequestSpecification requestSpecification = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token);
        Response response = requestSpecification.get("/Account/v1/User/" + userId);
        return response;
    }
}
