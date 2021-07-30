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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static java.util.Map.entry;
import static java.util.Objects.isNull;
import static models.UserAccount.UserKeys.*;

/**
 * This class represents the {@code user} JSON fragment from the fetched JSON file. The {@code user}
 * key is basically based on social statistics keys like: instagram username, likes, photos posted -
 * their portfolio links, location - hireable - first name, last name and download links for
 * different sizes of the user's profile image. Also, all the available user keys are noted in
 * {@link UserKeys}, the profile image sizes are noted at {@link PFPSizes} and lastly, all the
 * available social image keys are noted at {@link SocialKeys}.
 *
 * @see UserKeys
 * @see SocialKeys
 * @see PFPSizes
 * @see Serializable
 * @see Location
 * @see ImageDateTime
 *
 * @author ConfusedRobo
 */
@Author(
        author = "ConfusedRobo",
        creation = "Monday, 19 July, 2021, 05:34:35 PM",
        profile = "https://github.com/heretickeymaker"
)
public class UserAccount implements Serializable {
    /**
     * The serial version number field that will assist the JVM to correctly
     * cast/parse the object
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The profile size i.e. {@code profile_image} JSON fields will be stored here
     *
     * @see PFPSizes
     */
    public final Map<String, URL> profileImageSizeLinks;
    /**
     * The social link url JSON fields will be stored here
     *
     * @see SocialKeys
     */
    public final Map<String, URL> links;
    /**
     * The {@code id} JSON field
     */
    public String id;
    /**
     * The {@code username} JSON field
     */
    public String username;
    /**
     * The {@code name} JSON field
     */
    public String name;
    /**
     * The {@code first_name} JSON field
     */
    public String firstName;
    /**
     * The {@code last_name} JSON field
     */
    public String lastName;
    /**
     * The {@code bio} JSON field
     */
    public String bio;

    /**
     * The JSON {@code location} field where the city name will be stored
     *
     * @see Location
     */
    public Location location;
    /**
     * The {@code updated_at} JSON field
     */
    public ImageDateTime updatedAt;

    /**
     * The {@code instagram_username} JSON field
     */
    public String instagramUsername;
    /**
     * The {@code twitter_username} JSON field
     */
    public String twitterUsername;
    /**
     * The {@code portfolio_url} JSON field
     */
    public URL portfolioURL;

    /**
     * The {@code total_likes} JSON field
     */
    public long totalLikes;
    /**
     * The {@code total_photos} JSON field
     */
    public long totalPhotosPosted;
    /**
     * The {@code total_collections} JSON field
     */
    public long totalPhotoCollections;

    /**
     * The {@code for_hire} JSON field
     */
    public boolean hireable;
    /**
     * The {@code accepted_tos} JSON field
     */
    public boolean acceptedUnsplashTOS;

    /**
     * The default constructor that only initializes {@link UserAccount#profileImageSizeLinks} and
     * {@link UserAccount#links} fields with {@link HashMap}
     */
    public UserAccount() {
        profileImageSizeLinks = new HashMap<>(3);
        PFPSizes.getAllKeys().forEach(
                item -> profileImageSizeLinks.put(item, null)
        );

        links = new HashMap<>(50);
        SocialKeys.getAllKeys().forEach(
                item -> links.put(item, null)
        );
    }

    /**
     * This method will check for nullity for all objects if the said object turns out to be
     * {@code null} then {@link JSONObject#NULL} will be returned, otherwise that same object
     * will be returned
     *
     * @param object that will be checked if it's {@code null} or, not
     * @return an object that is either the same as param or, {@link JSONObject#NULL} if the param object
     * turns out to be {@code null}
     * @see JSONObject#NULL
     */
    private Object escapeNull(Object object) {
        return isNull(object) ? JSONObject.NULL : object;
    }

    /**
     * Packs all of the {@code user} field's {@code profile_images} nested JSON field into JSON
     *
     * @return a {@link JSONObject} object
     */
    public JSONObject profilesToJSON() {
        if (isNull(profileImageSizeLinks)) return null;
        var jsonBuilder = new JSONObject();
        profileImageSizeLinks.forEach(
                (key, value) -> jsonBuilder.put(key, escapeNull(value))
        );
        return jsonBuilder;
    }

    /**
     * Packs all of the {@code user} field's {@code social} nested JSON field into JSON
     *
     * @return a {@link JSONObject} object
     */
    public JSONObject socialsToJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put(INSTAGRAM_USERNAME, escapeNull(instagramUsername));
        jsonBuilder.put(PORTFOLIO_URL, escapeNull(portfolioURL));
        jsonBuilder.put(TWITTER_USERNAME, escapeNull(twitterUsername));
        return jsonBuilder;
    }

    /**
     * Packs all of the {@code user} field's {@code links} nested JSON field of urls into JSON
     *
     * @return a {@link JSONObject} object
     */
    public JSONObject linksToJSON() {
        var jsonBuilder = new JSONObject();
        links.forEach(
                (key, value) -> jsonBuilder.put(key, escapeNull(value))
        );
        return jsonBuilder;
    }

    /**
     * Packs or, assembles all of the {@code user} fields into one Unsplash
     * API compliant JSON
     *
     * @return a {@link JSONObject} object
     * @see UserKeys
     */
    public JSONObject toJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put(TOTAL_PHOTOS, escapeNull(totalPhotosPosted));
        jsonBuilder.put(ACCEPTED_TOS, escapeNull(acceptedUnsplashTOS));
        jsonBuilder.put(SOCIAL, escapeNull(socialsToJSON()));
        jsonBuilder.put(TWITTER_USERNAME, escapeNull(twitterUsername));
        jsonBuilder.put(LAST_NAME, escapeNull(lastName));
        jsonBuilder.put(BIO, escapeNull(bio));
        jsonBuilder.put(TOTAL_LIKES, totalLikes);
        jsonBuilder.put(PORTFOLIO_URL, escapeNull(portfolioURL));
        jsonBuilder.put(PROFILE_IMAGE, profilesToJSON());
        jsonBuilder.put(UPDATED_AT, escapeNull(updatedAt.source()));
        jsonBuilder.put(FOR_HIRE, hireable);
        jsonBuilder.put(NAME, name);
        jsonBuilder.put(LOCATION, escapeNull(location.getCity()));
        jsonBuilder.put(LINKS, linksToJSON());
        jsonBuilder.put(TOTAL_COLLECTIONS, totalPhotoCollections);
        jsonBuilder.put(ID, id);
        jsonBuilder.put(FIRST_NAME, firstName);
        jsonBuilder.put(INSTAGRAM_USERNAME, escapeNull(instagramUsername));
        jsonBuilder.put(USERNAME, username);
        return jsonBuilder;
    }

    /**
     * Adds an {@link Entry} of user profile picture url with the size key to the available
     * image sizes' list
     *
     * @param profileImageSizeLinks a map entry {@link Entry} for a user profile picture size link
     */
    public void addProfileSize(@NotNull Entry<String, URL> profileImageSizeLinks) {
        this.profileImageSizeLinks.put(profileImageSizeLinks.getKey(), profileImageSizeLinks.getValue());
    }

    /**
     * This method is similar to {@link UserAccount#addProfileSize(Entry)}. It's just that this method takes
     * two params and packs into a {@link Entry} i.e., the size of the image and the url link that points
     * to that image resource
     *
     * @param size the size of the user's profile image which should be picked out from {@link PFPSizes}
     * @param url  the url of the profile image
     */
    public void addProfileSize(String size, URL url) {addProfileSize(entry(size, url));}

    /**
     * This method accepts raw strings as their url and size key in which, the url string will be parsed into
     * {@link URL} format
     *
     * @param size      the size of the user's profile image which should be picked out from {@link PFPSizes}
     * @param urlSource the url source string for the profile image
     * @throws MalformedURLException if the said url to be parser's format is wrong
     */
    public void addProfileSize(String size, String urlSource) throws MalformedURLException {
        addProfileSize(size, new URL(urlSource));
    }

    /**
     * This method adds an array of entries all at once
     *
     * @param profileImageSizeLinks an array of {@link Entry}
     */
    @SafeVarargs
    public final void addAllSizes(@NotNull Entry<String, URL>... profileImageSizeLinks) {
        stream(profileImageSizeLinks).forEach(this::addProfileSize);
    }

    /**
     * This method adds a map of entries all at once
     *
     * @param profileImageSizeLinks a {@link Map} of all entries
     */
    public void addAllSizes(@NotNull Map<String, URL> profileImageSizeLinks) {
        this.profileImageSizeLinks.putAll(profileImageSizeLinks);
    }

    /**
     * Adds an {@link Entry} of the fetched image url with the size key to the available
     * image sizes' list
     *
     * @param socialLink an {@link Entry} instance that must contain the size key
     *                   from {@link SocialKeys} fields and an url pointing to that
     *                   image resource
     */
    public void addLink(@NotNull Entry<String, URL> socialLink) {
        links.put(socialLink.getKey(), socialLink.getValue());
    }

    /**
     * This method is similar to {@link UserAccount#addLink(Entry)}. It's just that this method takes two params
     * and packs into a {@link Entry} i.e., the social type key and the url link that points to that image
     * resource
     *
     * @param socialType the social type key which should be picked out from {@link SocialKeys}
     * @param url        the {@link URL} of the image resource
     */
    public void addLink(String socialType, URL url) {addLink(entry(socialType, url));}

    /**
     * This method adds all entries at once via. an {@link Entry} array
     *
     * @param socialLinks is the array of {@link Entry}
     */
    @SafeVarargs
    public final void addLinks(Entry<String, URL>... socialLinks) {stream(socialLinks).forEach(this::addLink);}

    /**
     * This method accepts raw strings as their url and social key in which, the url string will be
     * parsed into {@link URL} format
     *
     * @param socialType the social key that should be picked out from {@link SocialKeys}
     * @param urlSource  the resource pointing to that image resource
     * @throws MalformedURLException if the raw url string is in incorrect format
     */
    public void addLink(String socialType, String urlSource) throws MalformedURLException {
        addLink(socialType, new URL(urlSource));
    }

    /**
     * This class is a collection of all {@code profile} JSON key's value keys
     */
    public static abstract class PFPSizes {
        /**
         * The small profile image size link's key
         */
        public static String SMALL = "small";
        /**
         * The medium profile image size link's key
         */
        public static String MEDIUM = "medium";
        /**
         * The large profile image size link's key
         */
        public static String LARGE = "large";

        /**
         * Packs all of the {@code profile} keys into a list which may be used for
         * validation or, other purposes
         *
         * @return an unmodifiable {@link List} of keys
         */
        @Contract(pure = true)
        public static @NotNull @Unmodifiable List<String> getAllKeys() {return of(SMALL, MEDIUM, LARGE);}
    }

    /**
     * This class is a collection of all {@code user} JSON keys except {@code profile} and {@code socials} keys
     */
    public static abstract class UserKeys {
        /**
         * The total amount of photos posted key
         */
        public static String TOTAL_PHOTOS = "total_photos";
        /**
         * This key signifies whether the user has accepted the unsplash terms and conditions
         */
        public static String ACCEPTED_TOS = "accepted_tos";
        /**
         * The social nested JSON key
         */
        public static String SOCIAL = "social";
        /**
         * The Twitter username of the user key
         */
        public static String TWITTER_USERNAME = "twitter_username";
        /**
         * The last name of the user key
         */
        public static String LAST_NAME = "last_name";
        /**
         * The bio of the user key
         */
        public static String BIO = "bio";
        /**
         * The total likes on the user's profile key
         */
        public static String TOTAL_LIKES = "total_likes";
        /**
         * The portfolio url key
         */
        public static String PORTFOLIO_URL = "portfolio_url";
        /**
         * The portfolio url that points to the user's portfolio key
         */
        public static String PROFILE_IMAGE = "profile_image";
        /**
         * The last date the profile was updated/modified key
         */
        public static String UPDATED_AT = "updated_at";
        /**
         * This key signifies whether the user is hireable or not
         */
        public static String FOR_HIRE = "for_hire";
        /**
         * The name of the user key
         */
        public static String NAME = "name";
        /**
         * The location of the user
         */
        public static String LOCATION = "location";
        /**
         * The links key
         */
        public static String LINKS = "links";
        /**
         * The total photo albums/collections made by the user key
         */
        public static String TOTAL_COLLECTIONS = "total_collections";
        /**
         * The id of the user key
         */
        public static String ID = "id";
        /**
         * The first name of the user key
         */
        public static String FIRST_NAME = "first_name";
        /**
         * The Instagram username key
         */
        public static String INSTAGRAM_USERNAME = "instagram_username";
        /**
         * The username of the user key
         */
        public static String USERNAME = "username";

        /**
         * Packs all of the {@code user} keys into a list except {@code profile} and {@code socials} keys
         * which may be used for validation or, other purposes
         *
         * @return an unmodifiable {@link List} of keys
         */
        @Unmodifiable
        @Contract(pure = true)
        public static List<String> getAllKeys() {
            return of(
                    TOTAL_PHOTOS, ACCEPTED_TOS, SOCIAL, TWITTER_USERNAME, LAST_NAME, BIO, TOTAL_LIKES,
                    PORTFOLIO_URL, PROFILE_IMAGE, UPDATED_AT, FOR_HIRE, NAME, LOCATION, LINKS,
                    TOTAL_COLLECTIONS, ID, FIRST_NAME, INSTAGRAM_USERNAME, USERNAME
            );
        }
    }

    /**
     * This class is a collection of all {@code user} JSON keys except {@code profile} and {@code socials} keys
     */
    public static abstract class SocialKeys {
        /**
         * The total amount of unsplash followers key
         */
        public static final String FOLLOWERS = "followers";
        /**
         * The total number of unsplash accounts following key
         */
        public static final String FOLLOWING = "following";
        /**
         * The portfolio link key
         */
        public static final String PORTFOLIO = "portfolio";
        /**
         * The self profile link key
         */
        public static final String SELF = "self";
        /**
         * The html link key
         */
        public static final String HTML = "html";
        /**
         * The total number of photos posted key
         */
        public static final String PHOTOS = "photos";
        /**
         * The total number of likes key
         */
        public static final String LIKES = "likes";

        /**
         * Packs all of the {@code social} keys into a list and a few other that are related to
         * social statistics which may be used for validation or, other purposes
         *
         * @return an unmodifiable {@link List} of keys
         */
        @Contract(pure = true)
        public static @NotNull @Unmodifiable List<String> getAllKeys() {
            return of(FOLLOWERS, FOLLOWING, PORTFOLIO, SELF, HTML, PHOTOS, LIKES);
        }
    }
}
