package com.brewerytown.dynamicresizer.controller;

import com.brewerytown.dynamicresizer.model.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@RestController
@Path("/health")
public class HealthController {
    @GET
    @Produces({"application/json"})
    public Health health() {
        return new Health();
    }
}
