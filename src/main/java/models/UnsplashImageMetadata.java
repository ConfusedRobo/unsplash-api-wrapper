package models;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import models.model_utils.Dimension;

import static java.lang.System.err;

@SuppressWarnings("SpellCheckingInspection")
public class UnsplashImageMetadata implements Serializable {
    /**
     * The serial version number field that will assist the JVM to correctly
     * cast/parse the object
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private String blurhash;
    private String color;

    private UnsplashImageDateTimeData createdAt;
    private UnsplashImageDateTimeData updatedAt;

    private Dimension dimension;

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
    }

    public void setDimension(int width, int height) {
        this.dimension = new Dimension(width, height);
    }

    public JSONObject toJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("blurhash", this.blurhash);
        jsonBuilder.put("color", this.color);
        jsonBuilder.put("created_at", this.createdAt);
        jsonBuilder.put("updated_at", this.updatedAt);
        jsonBuilder.put("width", dimension.width);
        jsonBuilder.put("height", dimension.height);
        jsonBuilder.put("creation_date", this.createdAt.date());
        jsonBuilder.put("updation_date", this.updatedAt.date());
        return jsonBuilder;
    }

    public String getBlurhash() { return blurhash; }

    public String getColor() { return color; }

    public UnsplashImageDateTimeData getCreatedAt() { return createdAt; }

    public UnsplashImageDateTimeData getUpdatedAt() { return updatedAt; }

    public Dimension getDimension() { return dimension; }
}
