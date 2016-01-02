package com.brewerytown.dynamicresizer.controller;

import com.brewerytown.dynamicresizer.model.ImageSize;
import com.brewerytown.dynamicresizer.service.ResizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.StreamingOutput;

@Component
@RestController
@Path("/resize")
public class ResizeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResizeController.class);

    private final ResizeService resizeService;

    public ResizeController(ResizeService resizeService) {
        this.resizeService = resizeService;
    }

    @GET
    @Path("image/{image}/height/{height}/width/{width}")
    @Produces({"image/jpg"})
    public StreamingOutput resize(@PathParam("image") String image, @PathParam("height") Integer height, @PathParam("width") Integer width) {
        return resizeService.getStreamingOutput(image, ImageSize.of(height, width));
    }

    @GET
    @Path("image/{image}/sizeSpec/{sizeSpec}")
    @Produces({"image/jpg"})
    public StreamingOutput resize(@PathParam("image") String image, @PathParam("sizeSpec") String sizeSpec) {
        if ("SRC".equals(sizeSpec)) {
            return resizeService.getOriginalImage(image);
        } else {
            ImageSize imageSize = resizeService.getImageSizeFromSpec(sizeSpec);
            return resizeService.getStreamingOutput(image, imageSize);
        }
    }

}
