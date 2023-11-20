package endpoints;

import config.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;

public class PetStorePetEndPoint {

    public Response getPetById(String id) {
        return given()
                .pathParam("petId", id)
                .when()
                .get(Config.PET_BY_ID)
                .then().extract().response();
    }

    public Response getPetByStatus(String status) {
        return given()
                .queryParam("status", status)
                .when()
                .get(Config.PET_BY_STATUS)
                .then().extract().response();
    }

    public Response createPet(Pet pet) {
        return given()
                .body(pet)
                .when()
                .post(Config.CREATE_PET)
                .then().extract().response();
    }

    public Response deleteById(long id) {
        return given()
                .when()
                .delete(Config.PET_BY_ID, id)
                .then().extract().response();
    }

    public Response updatePet(Pet pet) {
        return given()
                .body(pet)
                .when()
                .put(Config.CREATE_PET)
                .then().extract().response();
    }

    private RequestSpecification given() {
        return RestAssured.given()
                .log().uri()
                .log().body()
                .baseUri(Config.PETSTORE_BASE_URL)
                .contentType(ContentType.JSON);
    }
}
