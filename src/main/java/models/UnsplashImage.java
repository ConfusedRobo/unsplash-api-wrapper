package models;

import models.model_utils.Location;
import org.json.JSONArray;

public class UnsplashImage {
    private final String id;
    private String sponsorship;
    private JSONArray currentUserCollections;

    private String description;
    private String altDescription;

    private UnsplashImageMetadata metadata;
    private UnsplashImageDateTimeData promotedAt;
    private Location location;

    private long downloads;
    private long views;
    private long likes;

    private boolean imageLikedByOP;

    public UnsplashImage(String id) { this.id = id; }

    public static class ImageSizeLinks {

    }
}
