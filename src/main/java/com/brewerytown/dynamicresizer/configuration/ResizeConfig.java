package com.brewerytown.dynamicresizer.configuration;

import com.brewerytown.dynamicresizer.controller.FullPathResizeController;
import com.brewerytown.dynamicresizer.controller.ResizeController;
import com.brewerytown.dynamicresizer.service.ResizeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResizeConfig {

    @Value("${images.path}")
    private String imagesPath;

    @Bean
    public ResizeService resizeService() {
        return new ResizeService(imagesPath);
    }

    @Bean
    public ResizeController resizeController() {
        return new ResizeController(resizeService());
    }

    @Bean
    public FullPathResizeController fullPathResizeController() {
        return new FullPathResizeController(resizeService());
    }
}
