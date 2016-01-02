package com.brewerytown.dynamicresizer.configuration;

import com.brewerytown.dynamicresizer.controller.FullPathResizeController;
import com.brewerytown.dynamicresizer.controller.HealthController;
import com.brewerytown.dynamicresizer.controller.ResizeController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HealthController.class);
        register(ResizeController.class);
        register(FullPathResizeController.class);
    }
}
