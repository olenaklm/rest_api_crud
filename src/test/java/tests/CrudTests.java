package tests;

import endpoints.PetStorePetEndPoint;
import io.restassured.response.Response;
import models.Pet;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CrudTests {

    @BeforeClass
    public static void cleanUp() {
        List<Pet> petListAvailable = new PetStorePetEndPoint()
                .getPetByStatus("available")
                .body()
                .jsonPath().getList("$", Pet.class);

        List<Pet> petListSold = new PetStorePetEndPoint()
                .getPetByStatus("sold")
                .body()
                .jsonPath().getList("$", Pet.class);

//        List<Pet> petList100 = new PetStorePetEndPoint()
//                .getPetByStatus("available")
//                .body()
//                .jsonPath().getList("findAll {item -> item.name == 'Murchyk100' }", Pet.class);
//
//        for (Pet pet : petList100) {
//            new PetStorePetEndPoint().deleteById(pet.getId());
//        }

        petListAvailable.stream().filter(pet -> "Murchyk100".equals(pet.getName()))
                .forEach(pet -> new PetStorePetEndPoint().deleteById(pet.getId()));

        petListSold.stream().filter(pet -> "Murchyk100".equals(pet.getName()))
                .forEach(pet -> new PetStorePetEndPoint().deleteById(pet.getId()));

        System.out.println();
    }


    @Test
    public void createPet() {
        // Given
        Pet murchyk = Pet.createCatPetAvailable(123125, "Murchyk100");
        // When
        Response petResponce = new PetStorePetEndPoint()
                .createPet(murchyk);
        // Then
        long createdPetId = petResponce
                .body()
                .as(Pet.class)
                .getId();

        Pet petCrearedFromService = new PetStorePetEndPoint()
                .getPetById(String.valueOf(createdPetId))
                .as(Pet.class);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(petCrearedFromService.getName()).as("Name").isEqualTo(murchyk.getName());
        assertions.assertThat(petCrearedFromService.getStatus()).as("Status").isEqualTo(murchyk.getStatus());
        assertions.assertAll();

    }

    @Test
    public void readPet() {
        // Given
        Pet murchyk = Pet.createCatPetAvailable(123125, "Murchyk100");
        Response petResponce = new PetStorePetEndPoint()
                .createPet(murchyk);
        long createdPetId = petResponce
                .body()
                .as(Pet.class)
                .getId();
        // When
        Pet petCrearedFromService = new PetStorePetEndPoint()
                .getPetById(String.valueOf(createdPetId))
                .as(Pet.class);
        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(petCrearedFromService.getName()).as("Name").isEqualTo(murchyk.getName());
        assertions.assertThat(petCrearedFromService.getStatus()).as("Status").isEqualTo(murchyk.getStatus());
        assertions.assertAll();

    }

    @Test
    public void updatePet() {
        // Given
        Pet murchyk = Pet.createCatPetAvailable(123125, "Murchyk100");
        Response petResponce = new PetStorePetEndPoint()
                .createPet(murchyk);

        murchyk.setStatus("sold");

        // When
        Pet petCrearedFromService = new PetStorePetEndPoint()
                .updatePet(murchyk).body().as(Pet.class);
        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(petCrearedFromService.getName()).as("Name").isEqualTo(murchyk.getName());
        assertions.assertThat(petCrearedFromService.getStatus()).as("Status").isEqualTo(murchyk.getStatus());
        assertions.assertAll();

    }

    @Test
    public void deletePet() {
        // Given
        Pet murchyk = Pet.createCatPetAvailable(123125, "Murchyk100");
        Response petResponce = new PetStorePetEndPoint()
                .createPet(murchyk);
        long createdPetId = petResponce
                .body()
                .as(Pet.class)
                .getId();
        Pet petCrearedFromService = new PetStorePetEndPoint()
                .getPetById(String.valueOf(createdPetId))
                .as(Pet.class);
        // When
        new PetStorePetEndPoint().deleteById(petCrearedFromService.getId());
        // Then
        Response petById = new PetStorePetEndPoint().getPetById(String.valueOf(petCrearedFromService.getId()));
//        Assertions.assertThat(petById.statusCode()).isEqualTo(200);
        Assertions.assertThat(petById.statusCode()).isEqualTo(404);


    }

}
