package com.brewerytown.dynamicresizer.controller;

import com.brewerytown.DynamicResizerApplication;
import com.brewerytown.dynamicresizer.model.Health;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DynamicResizerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class HealthControllerIT {

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void health() {
        ResponseEntity<Health> entity =
                restTemplate.getForEntity("http://localhost:9000/health", Health.class);

        assertThat(entity.getStatusCode().is2xxSuccessful(), Is.is(Boolean.TRUE));
        assertThat(entity.getBody().getStatus(), Is.is("ok"));
    }
}