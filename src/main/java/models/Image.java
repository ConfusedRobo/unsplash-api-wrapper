package models;

import models.model_utils.Location;
import org.json.JSONArray;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;

public class Image implements Serializable {
    /**
     * The serial version number field that will assist the JVM to correctly
     * cast/parse the object
     */
    @Serial
    private static final long serialVersionUID = 1L;
    public String id;
    public String sponsorship;
    public JSONArray currentUserCollections;

    public Map<String, URL> imageSizeLinks;
    public Map<String, URL> imageInfoLinks;

    public String description;
    public String altDescription;

    public ImageMetadata metadata;
    public ImageDateTime promotedAt;
    public CameraInfo exif;
    public UserAccount userAccountInfo;
    public Location location;

    public long downloads;
    public long views;
    public long likes;

    public boolean selfLikes;

    public interface ImageSizes {
        String RAW = "raw";
        String FULL = "full";
        String REGULAR = "regular";
        String small = "SMALL";
        String thumb = "thumb";
    }

    public interface ImageJSONKeys {
        String USER = "user";
    }
}
