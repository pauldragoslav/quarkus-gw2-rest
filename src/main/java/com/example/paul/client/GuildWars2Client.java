package com.example.paul.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/v2")
@RegisterRestClient(configKey="gw2-api")
@ApplicationScoped
public interface GuildWars2Client {

    @GET
    @Path("/account/achievements")
    @Produces("application/json")
    List<Achievement> getAccountAchievements(@QueryParam("access_token") String access_token);
}
