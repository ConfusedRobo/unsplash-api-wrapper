package models;

import models.model_utils.Location;
import org.json.JSONArray;

import java.net.URL;
import java.util.Map;

public class UnsplashImage {
    private final String id;
    private String sponsorship;
    private JSONArray currentUserCollections;

    private Map<String, URL> imageSizeLinks;
    private Map<String, URL> imageInfoLinks;

    private String description;
    private String altDescription;

    private UnsplashImageMetadata metadata;
    private UnsplashImageDateTimeData promotedAt;
    private UnsplashImageUserAccountInfo userAccountInfo;
    private Location location;

    private long downloads;
    private long views;
    private long likes;

    private boolean imageLikedByOP;

    public UnsplashImage(String id) { this.id = id; }

    public interface ImageSizes {
        String RAW = "raw";
        String FULL = "full";
        String REGULAR = "regular";
        String small = "SMALL";
        String thumb = "thumb";
    }
}
