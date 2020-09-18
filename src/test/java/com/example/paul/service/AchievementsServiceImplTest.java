package com.example.paul.service;

import com.example.paul.client.Achievement;
import com.example.paul.client.GuildWars2Client;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;

@QuarkusTest
class AchievementsServiceImplTest {

    @InjectMock
    @RestClient
    private GuildWars2Client guildWars2Client;

    @ConfigProperty(name = "gw2-api.key")
    String apiKey;
    @Inject
    AchievementsServiceImpl achievementsService;

    @Test
    void filtersOutCompletedAchievements() {
        var completeAchievementOne = new Achievement();
        completeAchievementOne.done = true;
        var completeAchievementTwo = new Achievement();
        completeAchievementTwo.done = true;
        var incompleteAchievementOne = new Achievement();
        incompleteAchievementOne.done = false;
        var incompleteAchievementTwo = new Achievement();
        incompleteAchievementTwo.done = false;
        when(guildWars2Client.getAccountAchievements(apiKey)).thenReturn(List.of(
                completeAchievementOne, completeAchievementTwo, incompleteAchievementOne, incompleteAchievementTwo));

        List<Achievement> results = achievementsService.getEasiestAchievements();

        List<Achievement> expectedAchievements = List.of(incompleteAchievementTwo, incompleteAchievementOne);
        Assertions.assertEquals(expectedAchievements, results);
    }

    @Test
    void ordersAchievementsByCompletionPercentageDescending() {
        var nearlyDoneAchievement = new Achievement();
        nearlyDoneAchievement.done = false;
        nearlyDoneAchievement.current = 74;
        nearlyDoneAchievement.max = 77;
        var inProgressAchievement = new Achievement();
        inProgressAchievement.done = false;
        inProgressAchievement.current = 23;
        inProgressAchievement.max = 64;
        var justStartedAchievement = new Achievement();
        justStartedAchievement.done = false;
        justStartedAchievement.current = 2;
        justStartedAchievement.max = 17;
        var notStartedAchievement = new Achievement();
        notStartedAchievement.done = false;
        notStartedAchievement.current = 0;
        notStartedAchievement.max = 123;
        when(guildWars2Client.getAccountAchievements(apiKey)).thenReturn(List.of(
                notStartedAchievement, justStartedAchievement, inProgressAchievement, nearlyDoneAchievement));

        List<Achievement> results = achievementsService.getEasiestAchievements();

        List<Achievement> expectedAchievements = List.of(
                nearlyDoneAchievement,
                inProgressAchievement,
                justStartedAchievement,
                notStartedAchievement);
        Assertions.assertEquals(expectedAchievements.get(0), results.get(0));
        Assertions.assertEquals(expectedAchievements.get(1), results.get(1));
        Assertions.assertEquals(expectedAchievements.get(2), results.get(2));
        Assertions.assertEquals(expectedAchievements.get(3), results.get(3));
    }

    @Test
    void returnsEmptyListWhenNoData() {
        when(guildWars2Client.getAccountAchievements(apiKey)).thenReturn(emptyList());

        List<Achievement> results = achievementsService.getEasiestAchievements();

        Assertions.assertEquals(emptyList(), results);
    }
}
