package com.brewerytown.dynamicresizer.model;

public class ImageSize {

    private final Integer height;
    private final Integer width;

    public static ImageSize of(Integer height, Integer width) {
        return new ImageSize(height, width);
    }

    public ImageSize(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }
}
