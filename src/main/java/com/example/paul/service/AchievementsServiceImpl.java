package com.example.paul.service;

import com.example.paul.client.Achievement;
import com.example.paul.client.GuildWars2Client;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

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
                .collect(toList());
    }
}
