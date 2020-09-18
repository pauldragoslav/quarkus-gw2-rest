package com.example.paul.service;

import com.example.paul.client.Achievement;
import com.example.paul.client.GuildWars2Client;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class AchievementsServiceImpl implements AchievementsService {

    private final GuildWars2Client guildWars2Client;
    private final String apiKey;

    public AchievementsServiceImpl(@RestClient GuildWars2Client guildWars2Client,
                                   @ConfigProperty(name = "gw2-api.key") String apiKey) {
        this.guildWars2Client = guildWars2Client;
        this.apiKey = apiKey;
    }

    @Override
    public List<Achievement> getEasiestAchievements() {
        List<Achievement> characterAchievements = guildWars2Client.getAccountAchievements(apiKey);

        return characterAchievements.stream()
                .filter(achievement -> !achievement.done)
                .sorted(new AchievementCompletionComparator().reversed())
//                .sorted(Comparator.comparing(AchievementsServiceImpl::completionPercentage).reversed())
                .collect(toList());
    }

    private static Integer completionPercentage(Achievement achievement) {
//        if (isNull(achievement.current) || isNull(achievement.max)) {
//            return Integer.MIN_VALUE;
//        }
//        if (achievement.current == 0) {
//            return Integer.MIN_VALUE + 1;
//        }
        return (achievement.current / achievement.max) * 100;
    }
}
