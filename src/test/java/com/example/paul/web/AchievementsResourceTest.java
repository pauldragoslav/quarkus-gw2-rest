package com.example.paul.web;

import com.example.paul.client.Achievement;
import com.example.paul.service.AchievementsService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class AchievementsResourceTest {

    @InjectMock
    private AchievementsService achievementsService;

    @Test
    void servesAchievementsResource() {
        var achievementOne = new Achievement();
        achievementOne.id = 1;
        achievementOne.current = 7;
        achievementOne.id = 9;
        achievementOne.done = true;
        achievementOne.repeated = 3;
        achievementOne.unlocked = true;
        var achievementTwo = new Achievement();
        achievementTwo.id = 2;
        achievementTwo.current = 1;
        achievementTwo.id = 100;
        achievementTwo.done = false;
        when(achievementsService.getEasiestAchievements()).thenReturn(List.of(achievementOne, achievementTwo));

        var expectedBody = "{\"achievements\":[{\"current\":7,\"done\":true,\"id\":9," +
                "\"repeated\":3,\"unlocked\":true},{\"current\":1,\"done\":false,\"id\":100}]}";
        given()
                .when().get("/achievement")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body(is(expectedBody));
    }
}
