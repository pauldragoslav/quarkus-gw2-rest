package com.example.paul.service;

import com.example.paul.client.Achievement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AchievementCompletionComparatorTest {

    private AchievementCompletionComparator underTest;

    @BeforeEach
    void setUp() {
        underTest = new AchievementCompletionComparator();
    }

    @Test
    void comparesCompleteAndNotStartedAchievements() {
        var a1 = new Achievement();
        a1.current = 0;
        a1.max = 100;
        var a2 = new Achievement();
        a2.current = 999;
        a2.max = 999;

        int result = underTest.compare(a1, a2);

        Assertions.assertEquals(-100, result);
    }

    @Test
    void comparesInProgressAchievements() {
        var a1 = new Achievement();
        a1.current = 24;
        a1.max = 48;
        var a2 = new Achievement();
        a2.current = 2;
        a2.max = 3;

        int result = underTest.compare(a1, a2);

        Assertions.assertEquals(-16, result);
    }

    @Test
    void comparesAchievementsWithInvalidDataFirst() {
        var a1 = new Achievement();
        var a2 = new Achievement();
        a2.current = 50;
        a2.max = 100;

        int result = underTest.compare(a1, a2);

        Assertions.assertEquals(-1, result);
    }

    @Test
    void comparesAchievementsWithInvalidDataSecond() {
        var a1 = new Achievement();
        a1.current = 50;
        a1.max = 100;
        var a2 = new Achievement();

        int result = underTest.compare(a1, a2);

        Assertions.assertEquals(1, result);
    }
}
