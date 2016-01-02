package com.brewerytown.dynamicresizer.controller;

import com.brewerytown.dynamicresizer.model.ImageSize;
import com.brewerytown.dynamicresizer.service.ResizeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;

@Component
@RestController
@Path("/images")
public class FullPathResizeController {
    private final ResizeService resizeService;

    public FullPathResizeController(ResizeService resizeService) {
        this.resizeService = resizeService;
    }

    @GET
    @Path("{imagePath: [a-zA-Z0-9_/\\-\\.]+}")
    @Produces({"image/jpg"})
    public StreamingOutput resize(@PathParam("imagePath") String imagePath) {
        String spec = imagePath.substring(imagePath.length() - 7, imagePath.length() - 4);

        String path = imagePath.replace("." + spec + ".", ".SRC.");

        if ("SRC".equals(spec)) {
            return resizeService.getOriginalImage(path);
        } else {
            ImageSize imageSize = resizeService.getImageSizeFromSpec(spec);
            return resizeService.getStreamingOutput(path, imageSize);
        }

    }
}
