package utils;

import interfaces.UnsplashRandom;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.net.http.HttpResponse.BodyHandlers;
import static java.util.Objects.isNull;
import static utils.paths.RandomPaths.RAND_CATEGORISED_SAVE_PATH;
import static utils.paths.RandomPaths.RAND_JSON_CACHE_SAVE_PATH;
import static utils.paths.TopPaths.ENV_FILEPATH;

/**
 * This class is a wrapper to generate a random image based on a specific topic like cats, dogs,
 * architecture, nature, scenery, etc. It does so by utilising the https://unsplash.com/ website's API.
 * This class supports multiple uses i.e., it is reusable but, in order to look for another category
 * the developer needs to call the {@code reset()} method and then the {@code init(String)} method
 * and pass the new category.
 *
 * @author ConfuesdRobo
 * @see UnsplashRandom
 * @see Iterable
 * @see Entry
 * @see Dotenv
 * @see JSONObject
 * @see Objects
 * @see HttpClient
 * @see HttpRequest
 * @see HttpResponse
 * @see TimeUnit
 * @see BodyHandlers
 * @see Consumer
 * @see Files
 * @see Path
 * @see CompletableFuture
 */
public class UnsplashRandomCategorised implements Serializable, UnsplashRandom, Iterable<Entry<String, Object>> {
    /**
     * The serial version number field that will assist the JVM to correctly
     * cast/parse the object
     *
     * @see Serializable
     * @see Serial
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The API key that will be stored here during runtime
     */
    private String client_id;
    /**
     * The category, tag or the topic on which you want to get the image on
     * for instance if the {@code category = "cat"} then the object should generate
     * a API call to the unsplash server for a random image of a cat
     */
    private String category;
    /**
     * The API call will actually return information about the image (download links, authors, etc.)
     * in JSON format which is in form of bytes.
     * Now, after the byte stream is converted or, encoded to {@link StandardCharsets#UTF_8} format
     * the string should be stored in this field for later use.
     */
    private String stringJSON;
    /**
     * The same string as stored in the {@link #stringJSON}
     * field variable, will be parsed here rather than just storing a sting with no semantics.
     *
     * @see JSONObject
     */
    private JSONObject cachedImage;

    /**
     * The default behavior; only the API key will be loaded
     *
     * @see #loadToken()
     */
    public UnsplashRandomCategorised() { loadToken(); }

    /**
     * This constructor assigns a category or a tag during declaration
     *
     * @param category the category of the image
     * @see #loadToken()
     */
    public UnsplashRandomCategorised(String category) {
        this.category = category;
        loadToken();
    }

    /**
     * This method loads the environment variable into runtime temporarily and removes from the env
     * after execution is completed
     *
     * @see Dotenv#configure()
     * @see DotenvBuilder#filename(String)
     * @see Dotenv#load()
     * @see Dotenv#get(String)
     */
    private void loadToken() { this.client_id = Dotenv.configure().filename(ENV_FILEPATH).load().get("API_KEY"); }

    /**
     * Checks if the {@code cachedImage} field and the {@code stringJSON} has been initialised or
     * not. Note that, this method is mainly used by {@link #toJSON()},
     * {@link #downloadLink()} and {@link #toJPG()}
     * before accessing those field's member methods so, they need to perform a check first to
     * ensure they are not null, or, else it'll yield a {@link NullPointerException}
     *
     * @return a {@code true} if {@link #cachedImage} and
     * {@link #stringJSON} fields are not {@code null}, {@code false} otherwise.
     * @see Objects#nonNull(Object)
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isInit() { return Objects.nonNull(cachedImage) && Objects.nonNull(stringJSON); }

    /**
     * This method method first checks if the API key is loaded into the runtime, if it is, then
     * the method performs an API call to the Unsplash server and fetches and encodes the bytes
     * into {@link StandardCharsets#UTF_8} format and then first assigns the encode string to the
     * {@link #stringJSON} field then assigns that same string to the
     * {@link #cachedImage} field, which adds semantics to that normal
     * string.
     *
     * @return true if the {@link #client_id} is loaded and also if
     * {@link #stringJSON} and {@link #cachedImage}
     * are assigned, false otherwise.
     * @see Objects#isNull(Object)
     * @see URI#create(String)
     * @see CompletableFuture
     * @see CompletableFuture#thenApply(Function)
     * @see CompletableFuture#thenAccept(Consumer)
     * @see CompletableFuture#get()
     * @see JSONObject
     * @see HttpResponse#body()
     * @see BodyHandlers#ofString()
     * @see HttpRequest#newBuilder()
     * @see HttpClient#newHttpClient()
     * @see HttpClient#sendAsync(HttpRequest, HttpResponse.BodyHandler)
     * @see TimeUnit#SECONDS
     * @see ExecutionException
     * @see InterruptedException
     * @see TimeoutException
     */
    @Override
    @SuppressWarnings("DuplicatedCode")
    public boolean init() {
        if (isNull(client_id)) return false;
        var prepURLString = API_LINK.replace("API_KEY", client_id) + LINK_SUBPART_QUERY
                .replace("CATEGORY", category);
        var request = HttpRequest.newBuilder(URI.create(prepURLString)).build();
        var client = HttpClient.newHttpClient();

        try {
            var rawJSONBuilder = new StringBuilder(500);
            // sending request
            client.sendAsync(request, BodyHandlers.ofString())
                  .thenApply(HttpResponse::body)
                  .thenAccept(rawJSONBuilder::append)
                  .get(2, TimeUnit.SECONDS);
            this.stringJSON = rawJSONBuilder.toString();
            this.cachedImage = new JSONObject(this.stringJSON);
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException exception) { return false; }
    }

    /**
     * Same as {@link #init()} only that it allows you to update the
     * {@link #category} field on initialization
     *
     * @param category the topic or genre of random image ou want to generate
     * @return {@code true} is the image has been generated without any errors, {@code false} otherwise
     */
    public boolean init(String category) {
        this.category = category;
        return init();
    }

    /**
     * This method taps into the download link which is provided in the JSON and fetches the actual
     * image in the form of byte stream which can be written to an empty image file.
     *
     * @return {@code byte[]} which is the image's byte stream, {@code null} if any {@link IOException}
     * occurs
     */
    private byte[] getImageBytes() {
        try (var stream = new URL(downloadLink()).openStream()) {
            return stream.readAllBytes();
        } catch (IOException exception) { return null; }
    }

    /**
     * Writes the {@link #stringJSON} to a file
     *
     * @return {@code true} if no {@link IOException} occurs, {@code false} otherwise
     * @see Path#of(String, String...)
     * @see Files#deleteIfExists(Path)
     * @see Files#writeString(Path, CharSequence, OpenOption...)
     */
    @Override
    public boolean toJSON() {
        if (!isInit()) return false;
        try {
            var prepPath = Path.of(RAND_JSON_CACHE_SAVE_PATH + "/" + "sample-tag.json");
            Files.deleteIfExists(prepPath);
            Files.writeString(prepPath, stringJSON);
            return true;
        } catch (IOException exception) { return false; }
    }

    /**
     * This is a utility method, it just fetches the download link from the JSON and returns it.
     *
     * @return a {@code String} that'll contain the download URL, {@code null} otherwise
     * @see JSONObject#getString(String)
     * @see JSONObject#getJSONObject(String)
     */
    @Override
    public String downloadLink() {
        if (!isInit()) return null;
        return this.cachedImage.getJSONObject("links").getString("download");
    }

    /**
     * This method fetches the {@link #getImageBytes()} and then writes
     * those bytes into a JPG file
     *
     * @param filename the name of the file
     * @return {@code true} if the image has been written successfully, {@code false}
     * if {@link IOException} or, any other error occurs
     * @see IOException
     * @see Objects#isNull(Object)
     * @see #isInit()
     * @see Files#write(Path, byte[], OpenOption...)
     * @see Path#of(String, String...)
     */
    @Override
    public boolean toJPG(String filename) {
        if (!isInit()) return false;
        var imageBytes = getImageBytes();
        if (isNull(imageBytes)) return false;

        try {
            var prepSavePath = Path.of(RAND_CATEGORISED_SAVE_PATH + "/" + filename + ".jpg");
            Files.write(prepSavePath, imageBytes);
            return true;
        } catch (IOException exception) { return false; }
    }

    /**
     * Creates an image file and giving it a name based on its id and category separated
     * by {@code "-"}
     *
     * @return {@code true} if
     */
    @Override
    public boolean toJPG() {
        var imageID = cachedImage.getString("id");
        var filename = String.format("%s-%s", category.strip().replace(' ', '-'), imageID);
        return toJPG(filename);
    }

    /**
     * Resets the class fields i.e., you'd need to call {@link #init()} or
     * {@link #init(String)} again in order to reassign/prime the fields for
     * another use.
     */
    @Override
    public void reset() {
        this.cachedImage = null;
        this.stringJSON = null;
        this.category = null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     * @see Entry
     * @see JSONObject#toMap()
     * @see Map#entrySet()
     */
    @NotNull
    @Override
    public Iterator<Entry<String, Object>> iterator() { return cachedImage.toMap().entrySet().iterator(); }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side-effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     *                              <pre>{@code
     *                                  for (T t : this)
     *                                      action.accept(t);
     *                              }</pre>
     * @see Consumer
     * @see Iterable
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super Entry<String, Object>> action) { Iterable.super.forEach(action); }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     * @see Objects#nonNull(Object)
     */
    @Override
    public String toString() {
        var clientLoaded = Objects.nonNull(client_id);
        var imageLoaded = Objects.nonNull(cachedImage);
        var categorySet = Objects.nonNull(category);
        return "Loaded {TOKEN=" + clientLoaded + ", IMAGE=" + imageLoaded + ", TAG=" + categorySet + "}";
    }
}
