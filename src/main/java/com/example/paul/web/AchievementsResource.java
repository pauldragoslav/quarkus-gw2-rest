package com.example.paul.web;

import com.example.paul.service.AchievementsService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
public class AchievementsResource {

    private final AchievementsService achievementsService;

    public AchievementsResource(AchievementsService achievementsService) {
        this.achievementsService = achievementsService;
    }

    @GET
    @Path("/achievement")
    @Produces(APPLICATION_JSON)
    public AchievementsDTO retrieveAchievements() {
        return new AchievementsDTO(achievementsService.getEasiestAchievements());
    }
}
