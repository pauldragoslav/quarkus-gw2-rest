package com.example.paul.web;

import com.example.paul.client.Achievement;

import java.util.List;
import java.util.Objects;

public class AchievementsDTO {

    private final List<Achievement> achievements;

    public AchievementsDTO(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AchievementsDTO that = (AchievementsDTO) o;
        return Objects.equals(achievements, that.achievements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievements);
    }

    @Override
    public String toString() {
        return "AchievementDTO{" +
                "achievements=" + achievements +
                '}';
    }
}
