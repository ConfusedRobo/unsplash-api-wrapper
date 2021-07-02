package models;

import models.model_utils.Location;

import java.net.URL;

public class UnsplashImageUserAccountInfo {
    private final String id;
    private String username;

    private String name;
    private String firstName;
    private String lastName;
    private String bio;

    private Location location;
    private UnsplashImageDateTimeData updatedAt;
    private UnsplashImageDateTimeData createdAt;

    private String instagramUsername;
    private String twitterUsername;
    private URL portfolioURL;

    private long totalPhotosLiked;
    private long totalPhotosPosted;
    private long totalPhotoCollections;

    private boolean hireable;
    private boolean acceptedUnsplashTOS;

    public UnsplashImageUserAccountInfo(String id) { this.id = id; }

    public UnsplashImageUserAccountInfo(String id, String username) { this(id); this.username = username; }
}
