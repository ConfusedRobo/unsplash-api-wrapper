package models;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;

import static java.lang.System.err;

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

    public void setColor(String color) {
        if (!color.startsWith("#")) { err.println("Format not supported. Must be in HEX"); return; }
        this.color = color;
    }

    public void setCreatedAt(UnsplashImageDateTimeData createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(UnsplashImageDateTimeData updatedAt) { this.updatedAt = updatedAt; }

    public void setCreatedAt(String createdAt) {
        this.createdAt = new UnsplashImageDateTimeData(createdAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = new UnsplashImageDateTimeData(updatedAt);
    }

    public void setDimension(@NotNull Dimension dimension) {
        this.dimension = dimension;
        this.width = dimension.width;
        this.height = dimension.height;
    }

    public void setDimension(int width, int height) {
        this.dimension = new Dimension(width, height);
        this.width = width;
        this.height = height;
    }

    public JSONObject packMDasJSONNative() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("blurhash", this.blurhash);
        jsonBuilder.put("color", this.color);
        jsonBuilder.put("created_at", this.createdAt);
        jsonBuilder.put("updated_at", this.updatedAt);
        jsonBuilder.put("dimension", this.dimension);
        jsonBuilder.put("width", this.width);
        jsonBuilder.put("height", this.height);
        jsonBuilder.put("creation_date", this.createdAt.getDate());
        jsonBuilder.put("updation_date", this.updatedAt.getDate());
        return jsonBuilder;
    }

    public JSONObject packMDasJSONStringValued() {
        var jsonNative = packMDasJSONNative();
        var jsonBuilder = new JSONObject();
        jsonNative.toMap().forEach((key, value) -> jsonBuilder.put(key, value.toString()));
        return jsonBuilder;
    }

    public String getBlurhash() { return blurhash; }

    public String getColor() { return color; }

    public UnsplashImageDateTimeData getCreatedAt() { return createdAt; }

    public UnsplashImageDateTimeData getUpdatedAt() { return updatedAt; }

    public Dimension getDimension() { return dimension; }
}
