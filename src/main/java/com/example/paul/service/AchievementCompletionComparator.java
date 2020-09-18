package com.example.paul.service;

import com.example.paul.client.Achievement;

import java.util.Comparator;

import static java.util.Objects.isNull;

public class AchievementCompletionComparator implements Comparator<Achievement> {

    @Override
    public int compare(Achievement a1, Achievement a2) {
        // Handle any bad data
        if (isNull(a1.current) || isNull(a1.max) || a1.max == 0) {
            return -1;
        } else if (isNull(a2.current) || isNull(a2.max) || a2.max == 0) {
            return 1;
        }

        return completionPercentage(a1) - completionPercentage(a2);
    }

    private Integer completionPercentage(Achievement achievement) {
        return achievement.current * 100 / achievement.max;
    }
}
