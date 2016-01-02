package com.brewerytown.dynamicresizer.service;

import com.brewerytown.dynamicresizer.model.ImageSize;
import jersey.repackaged.com.google.common.base.Stopwatch;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ResizeService {

    private static Properties properties;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResizeService.class);

    private final String imagesPath;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("specs.properties");
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (Exception e) {
            LOGGER.warn("Could not initialize properties");
            properties = null;
        }
    }

    public ResizeService(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public StreamingOutput getStreamingOutput(String path, ImageSize imageSize) {

        Stopwatch stopWatch = Stopwatch.createStarted();

        BufferedImage read = readImage(path);

        BufferedImage resize = null;
        try {
            resize = Thumbnails.of(read).size(imageSize.getWidth(), imageSize.getHeight()).keepAspectRatio(true).crop(Positions.CENTER).asBufferedImage();
        } catch (IOException e) {
            LOGGER.warn("Error resizing image", e);
        }

        stopWatch.stop();

        LOGGER.info(String.format("time resizing: %s",stopWatch.elapsed(TimeUnit.MILLISECONDS)));
        final BufferedImage finalResize = resize;

        return getOutput(finalResize);
    }

    private BufferedImage readImage(String path) {

        File file = new File(imagesPath + path);
        BufferedImage read = null;
        try {
            read = Imaging.getBufferedImage(file);
        } catch (ImageReadException e) {
            LOGGER.warn("Error reading image", e);
        } catch (IOException e) {
            LOGGER.warn("Error reading image", e);
        }
        return read;
    }

    private StreamingOutput getOutput(final BufferedImage finalResize) {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                ImageIO.write(finalResize, "jpg", output);
            }
        };
    }

    public ImageSize getImageSizeFromSpec(String sizeSpec) {
        String sizeSpecValue = properties.getProperty(sizeSpec);
        String[] split = sizeSpecValue.split(",");
        Integer height = Integer.valueOf(split[0]);
        Integer width = Integer.valueOf(split[1]);
        return ImageSize.of(height, width);
    }

    public StreamingOutput getOriginalImage(String path) {
        BufferedImage bufferedImage = this.readImage(path);
        return this.getOutput(bufferedImage);
    }
}
