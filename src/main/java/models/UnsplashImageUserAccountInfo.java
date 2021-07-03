package models;

import annotations.Author;
import models.model_utils.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static java.util.Objects.isNull;
import static models.UnsplashImageUserAccountInfo.UserKeys.*;

@SuppressWarnings("unused")
@Author(
        author = "ConfusedRobo",
        creation = "02-07-2021",
        profile = "https://github.com/ConfusedRobo"
)
public class UnsplashImageUserAccountInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 238493282932L;

    private final String id;
    private String username;

    private final Map<String, URL> profileImageSizeLinks;
    private final Map<String, URL> socialLinks;

    private String name;
    private String firstName;
    private String lastName;
    private String bio;

    private Location location;
    private UnsplashImageDateTimeData updatedAt;

    private String instagramUsername;
    private String twitterUsername;
    private URL portfolioURL;

    private long totalLikes;
    private long totalPhotosPosted;
    private long totalPhotoCollections;

    private boolean hireable;
    private boolean acceptedUnsplashTOS;

    public UnsplashImageUserAccountInfo(String id) {
        this.id = id;

        profileImageSizeLinks = new HashMap<>(3);
        ProfileImageSizes.getAllKeys().forEach(
                item -> profileImageSizeLinks.put(item, null)
        );

        socialLinks = new HashMap<>(50);
        SocialKeys.getAllKeys().forEach(
                item -> socialLinks.put(item, null)
        );
    }

    public UnsplashImageUserAccountInfo(String id, String username) {
        this(id);
        this.username = username;
    }

    private Object escapeNull(Object value) {
        return isNull(value) ? JSONObject.NULL : value;
    }

    public JSONObject packPFPSJSON() {
        if (isNull(profileImageSizeLinks)) return null;
        var jsonBuilder = new JSONObject();
        profileImageSizeLinks.forEach(
                (key, value) -> jsonBuilder.put(key, escapeNull(value))
        ); return jsonBuilder;
    }

    public JSONObject packSocialsJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put(INSTAGRAM_USERNAME, escapeNull(instagramUsername));
        jsonBuilder.put(PORTFOLIO_URL, escapeNull(portfolioURL));
        jsonBuilder.put(TWITTER_USERNAME, escapeNull(twitterUsername));
        return jsonBuilder;
    }

    public JSONObject packSocialStatLinksJSON() {
        var jsonBuilder = new JSONObject();
        socialLinks.forEach(
                (key, value) -> jsonBuilder.put(key, escapeNull(value))
        ); return jsonBuilder;
    }

    public JSONObject packUserJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put(TOTAL_PHOTOS, escapeNull(totalPhotosPosted));
        jsonBuilder.put(ACCEPTED_TOS, escapeNull(acceptedUnsplashTOS));
        jsonBuilder.put(SOCIAL, escapeNull(packSocialsJSON()));
        jsonBuilder.put(TWITTER_USERNAME, escapeNull(twitterUsername));
        jsonBuilder.put(LAST_NAME, escapeNull(lastName));
        jsonBuilder.put(BIO, escapeNull(bio));
        jsonBuilder.put(TOTAL_LIKES, totalLikes);
        jsonBuilder.put(PORTFOLIO_URL, escapeNull(portfolioURL));
        jsonBuilder.put(PROFILE_IMAGE, packPFPSJSON());
        jsonBuilder.put(UPDATED_AT, escapeNull(updatedAt.getDateSource()));
        jsonBuilder.put(FOR_HIRE, hireable);
        jsonBuilder.put(NAME, name);
        jsonBuilder.put(LOCATION, escapeNull(location.getCity()));
        jsonBuilder.put(LINKS, packSocialStatLinksJSON());
        jsonBuilder.put(TOTAL_COLLECTIONS, totalPhotoCollections);
        jsonBuilder.put(ID, id);
        jsonBuilder.put(FIRST_NAME, firstName);
        jsonBuilder.put(INSTAGRAM_USERNAME, escapeNull(instagramUsername));
        jsonBuilder.put(USERNAME, username);
        return jsonBuilder;
    }

    public void addImageSize(@NotNull Map.Entry<String, URL> profileImageSizeLink) {
        this.profileImageSizeLinks.put(
                profileImageSizeLink.getKey(),
                profileImageSizeLink.getValue()
        );
    }

    public void addImageSize(String size, URL url) { addImageSize(Map.entry(size, url)); }

    public void addImageSize(String size, String urlSource) throws MalformedURLException {
        addImageSize(size, new URL(urlSource));
    }

    @SafeVarargs
    public final void addAllSizes(@NotNull Map.Entry<String, URL>... profileImageSizeLinks) {
        Arrays.stream(profileImageSizeLinks)
              .forEach(this::addImageSize);
    }

    public void addAllSizes(@NotNull Map<String, URL> profileImageSizeLinks) {
        this.profileImageSizeLinks.putAll(profileImageSizeLinks);
    }

    public void addSocialLink(Map.Entry<String, URL> socialLink) {
        socialLinks.put(
                socialLink.getKey(),
                socialLink.getValue()
        );
    }

    @SafeVarargs
    public final void addSocialLinks(Map.Entry<String, URL>... socialLinks) {
        Arrays.stream(socialLinks).forEach(this::addSocialLink);
    }

    public void addSocialLink(String socialType, URL url) {
        addSocialLink(Map.entry(socialType, url));
    }

    public void addSocialLink(String socialType, String urlSource) throws MalformedURLException {
        addSocialLink(socialType, new URL(urlSource));
    }

    public Map<String, URL> getProfileImageSizeLinks() { return this.profileImageSizeLinks; }

    public Map<String, URL> getSocialLinks() { return socialLinks; }

    public String getId() { return id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public Location getLocation() { return location; }

    public String getLocationTitle() { return location.getTitle(); }

    public void setLocation(Location location) { this.location = location; }

    public UnsplashImageDateTimeData getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(UnsplashImageDateTimeData updatedAt) { this.updatedAt = updatedAt; }

    public String getInstagramUsername() { return instagramUsername; }

    public void setInstagramUsername(String instagramUsername) { this.instagramUsername = instagramUsername; }

    public String getTwitterUsername() { return twitterUsername; }

    public void setTwitterUsername(String twitterUsername) { this.twitterUsername = twitterUsername; }

    public URL getPortfolioURL() { return portfolioURL; }

    public void setPortfolioURL(URL portfolioURL) { this.portfolioURL = portfolioURL; }

    public long getTotalLikes() { return totalLikes; }

    public void setTotalLikes(long totalLikes) { this.totalLikes = totalLikes; }

    public long getTotalPhotosPosted() { return totalPhotosPosted; }

    public void setTotalPhotosPosted(long totalPhotosPosted) { this.totalPhotosPosted = totalPhotosPosted; }

    public long getTotalPhotoCollections() { return totalPhotoCollections; }

    public void setTotalPhotoCollections(long totalPhotoCollections) {
        this.totalPhotoCollections = totalPhotoCollections;
    }

    public boolean isHireable() { return hireable; }

    public void setHireable(boolean hireable) { this.hireable = hireable; }

    public boolean hasAcceptedTOS() { return acceptedUnsplashTOS; }

    public void setAcceptedUnsplashTOS(boolean acceptedUnsplashTOS) {
        this.acceptedUnsplashTOS = acceptedUnsplashTOS;
    }

    public static abstract class ProfileImageSizes {
        public static String SMALL = "small";
        public static String MEDIUM = "medium";
        public static String LARGE = "large";

        public static List<String> getAllKeys() {
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

    public static abstract class SocialKeys {
        public static final String FOLLOWERS = "followers";
        public static final String FOLLOWING = "following";
        public static final String PORTFOLIO = "portfolio";
        public static final String SELF = "self";
        public static final String HTML = "html";
        public static final String PHOTOS = "photos";
        public static final String LIKES = "likes";

        public static List<String> getAllKeys() {
            return List.of(
                    FOLLOWERS,
                    FOLLOWING,
                    PORTFOLIO,
                    SELF,
                    HTML,
                    PHOTOS,
                    LIKES
            );
        }
    }
}
