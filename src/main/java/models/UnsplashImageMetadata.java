package models;

import java.awt.*;

@SuppressWarnings("SpellCheckingInspection")
public class UnsplashImageMetadata {

    private String blurhash;
    private String color;

    private UnsplashImageDateTimeData createdAt;
    private UnsplashImageDateTimeData updatedAt;

    private Dimension dimension;
    private int height;
    private int width;

    public void setBlurhash(String blurhash) { this.blurhash = blurhash; }

    public void setColor(String color) { this.color = color; }

    public void setCreatedAt(UnsplashImageDateTimeData createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(UnsplashImageDateTimeData updatedAt) { this.updatedAt = updatedAt; }

    public void setCreatedAt(String createdAt) {
        this.createdAt = new UnsplashImageDateTimeData(createdAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = new UnsplashImageDateTimeData(updatedAt);
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
        this.width = dimension.width;
        this.height = dimension.height;
    }

    public void setDimension(int width, int height) {
        this.dimension = new Dimension(width, height);
        this.width = width;
        this.height = height;
    }

    public String getBlurhash() { return blurhash; }

    public String getColor() { return color; }

    public UnsplashImageDateTimeData getCreatedAt() { return createdAt; }

    public UnsplashImageDateTimeData getUpdatedAt() { return updatedAt; }

    public Dimension getDimension() { return dimension; }
}
