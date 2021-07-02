package models;

import annotations.Author;
import models.model_utils.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
@Author(
        author = "ConfusedRobo",
        creation = "02-07-2021",
        profile = "https://github.com/ConfusedRobo"
)
public class UnsplashImageUserAccountInfo {
    private final String id;
    private String username;

    private Map<String, URL> profileImageSizeLinks;
    private Map<String, URL> userInfoLinks;

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

    public UnsplashImageUserAccountInfo(String id, String username) {
        this(id);
        this.username = username;
    }

    public String getId() { return id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public Map<String, URL> getProfileImageSizeLinks() {
        return profileImageSizeLinks;
    }

    public JSONObject packPFPSJSON() {
        if (Objects.isNull(profileImageSizeLinks)) return null;
        var jsonBuilder = new JSONObject();
        profileImageSizeLinks.forEach((key, value) -> jsonBuilder.put(key, value.toString()));
        return jsonBuilder;
    }

    public JSONObject packPFPSJSONAPI() {
        var normalImageLinksJSON = packPFPSJSON();
        if (Objects.isNull(normalImageLinksJSON)) return null;
        var jsonBuilder = new JSONObject();
        jsonBuilder.put(UserKeys.PROFILE_IMAGE, normalImageLinksJSON);
        return jsonBuilder;
    }

    public void addImageSize(@NotNull Map.Entry<String, URL> profileImageSizeLink) {
        var containsImage = ProfileImageSizes
                .getAllSizes()
                .contains(profileImageSizeLink.getKey());
        if (containsImage) System.err.println("Already has this size");
        this.profileImageSizeLinks.put(profileImageSizeLink.getKey(), profileImageSizeLink.getValue());
    }

    public void addImageSize(String size, String urlSource) throws MalformedURLException {
        addImageSize(Map.entry(size, new URL(urlSource)));
    }

    @SafeVarargs
    public final void addAllSizes(@NotNull Map.Entry<String, URL>... profileImageSizeLinks) {
        for (var entry : profileImageSizeLinks) addImageSize(entry);
    }

    public void addAllSizes(@NotNull Map<String, URL> profileImageSizeLinks) {
        var entries = profileImageSizeLinks.entrySet();
        for (var entry : entries) addImageSize(entry);
    }

    public Map<String, URL> getUserInfoLinks() {
        return userInfoLinks;
    }

    public void setUserInfoLinks(Map<String, URL> userInfoLinks) {
        this.userInfoLinks = userInfoLinks;
    }

    public JSONObject packUserInfoJSON() {
        var jsonBuilder = new JSONObject();
        return null;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public UnsplashImageDateTimeData getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(UnsplashImageDateTimeData updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UnsplashImageDateTimeData getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(UnsplashImageDateTimeData createdAt) {
        this.createdAt = createdAt;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public URL getPortfolioURL() {
        return portfolioURL;
    }

    public void setPortfolioURL(URL portfolioURL) {
        this.portfolioURL = portfolioURL;
    }

    public long getTotalPhotosLiked() {
        return totalPhotosLiked;
    }

    public void setTotalPhotosLiked(long totalPhotosLiked) {
        this.totalPhotosLiked = totalPhotosLiked;
    }

    public long getTotalPhotosPosted() {
        return totalPhotosPosted;
    }

    public void setTotalPhotosPosted(long totalPhotosPosted) {
        this.totalPhotosPosted = totalPhotosPosted;
    }

    public long getTotalPhotoCollections() {
        return totalPhotoCollections;
    }

    public void setTotalPhotoCollections(long totalPhotoCollections) {
        this.totalPhotoCollections = totalPhotoCollections;
    }

    public boolean isHireable() {
        return hireable;
    }

    public void setHireable(boolean hireable) {
        this.hireable = hireable;
    }

    public boolean isAcceptedUnsplashTOS() {
        return acceptedUnsplashTOS;
    }

    public void setAcceptedUnsplashTOS(boolean acceptedUnsplashTOS) {
        this.acceptedUnsplashTOS = acceptedUnsplashTOS;
    }

    public static abstract class ProfileImageSizes {
        public static String SMALL = "small";
        public static String MEDIUM = "medium";
        public static String LARGE = "large";

        public static List<String> getAllSizes() {
            return List.of(
                    SMALL,
                    MEDIUM,
                    LARGE
            );
        }
    }

    public static abstract class UserKeys {
        public static String TOTAL_PHOTOS = "total_photos";
        public static String ACCEPTED_TOS = "accepted_tos";
        public static String SOCIAL = "social";
        public static String TWITTER_USERNAME = "twitter_username";
        public static String LAST_NAME = "last_name";
        public static String BIO = "bio";
        public static String TOTAL_LIKES = "total_likes";
        public static String PORTFOLIO_URL = "portfolio_url";
        public static String PROFILE_IMAGE = "profile_image";
        public static String UPDATED_AT = "updated_at";
        public static String FOR_HIRE = "for_hire";
        public static String NAME = "name";
        public static String LOCATION = "location";
        public static String LINKS = "links";
        public static String TOTAL_COLLECTIONS = "total_collections";
        public static String ID = "id";
        public static String FIRST_NAME = "first_name";
        public static String INSTAGRAM_USERNAME = "instagram_username";
        public static String USERNAME = "username";

        @Unmodifiable
        @Contract(pure = true)
        public static List<String> getAllKeys() {
            return List.of(
                    TOTAL_PHOTOS,
                    ACCEPTED_TOS,
                    SOCIAL,
                    TWITTER_USERNAME,
                    LAST_NAME,
                    BIO,
                    TOTAL_LIKES,
                    PORTFOLIO_URL,
                    PROFILE_IMAGE,
                    UPDATED_AT,
                    FOR_HIRE,
                    NAME,
                    LOCATION,
                    LINKS,
                    TOTAL_COLLECTIONS,
                    ID,
                    FIRST_NAME,
                    INSTAGRAM_USERNAME,
                    USERNAME
            );
        }
    }
}
