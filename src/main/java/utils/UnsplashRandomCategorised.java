package utils;

import interfaces.UnsplashRandom;
import io.github.cdimascio.dotenv.Dotenv;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static java.net.http.HttpResponse.BodyHandlers;
import static utils.paths.RandomPaths.RAND_CATEGORISED_SAVE_PATH;
import static utils.paths.RandomPaths.RAND_JSON_CACHE_SAVE_PATH;
import static utils.paths.TopPaths.ENV_FILEPATH;

@SuppressWarnings("ALL")
public class UnsplashRandomCategorised implements Serializable, UnsplashRandom, Iterable<Map.Entry<String, Object>> {
    @Serial
    private static final long serialVersionUID = 1L;
    private String client_id;

    private String category;
    private String stringJSON;
    private JSONObject cachedImage;

    public UnsplashRandomCategorised(String category) { this.category = category; }

    private void loadToken() { this.client_id = Dotenv.configure().filename(ENV_FILEPATH).load().get("API_KEY"); }

    private boolean isInitialized() { return Objects.nonNull(cachedImage) && Objects.nonNull(stringJSON); }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public boolean init() {
        loadToken();
        if (Objects.isNull(client_id)) return false;

        var prepURLString = API_LINK.replace("API_KEY", client_id) + LINK_SUBPART_QUERY.replace("CATEGORY", category);
        var request = HttpRequest.newBuilder(URI.create(prepURLString)).build();
        var client = HttpClient.newHttpClient();

        try {
            var rawJSONBuilder = new StringBuilder(500);
            client.sendAsync(request, BodyHandlers.ofString())
                  .thenApply(HttpResponse::body)
                  .thenAccept(rawJSONBuilder::append)
                  .get(2, TimeUnit.SECONDS);
            this.stringJSON = rawJSONBuilder.toString();
            this.cachedImage = new JSONObject(this.stringJSON);
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException exception) { return false; }
    }

    private byte[] getImageBytes() {
        try (var stream = new URL(downloadLink()).openStream()) {
            return stream.readAllBytes();
        } catch (IOException exception) { return null; }
    }

    @Override
    public boolean saveImageAsJSONFile() {
        if (!isInitialized()) return false;
        try {
            var prepPath = Path.of(RAND_JSON_CACHE_SAVE_PATH + "/" + "sample-tag.json");
            Files.deleteIfExists(prepPath);
            Files.writeString(prepPath, stringJSON);
            return true;
        } catch (IOException exception) { return false; }
    }

    @Override
    public String downloadLink() {
        if (!isInitialized()) return null;
        return this.cachedImage.getJSONObject("links").getString("download");
    }

    @Override
    public boolean saveImageAsJPG() {
        var imageID = cachedImage.getString("id");
        var filename = String.format("%s-%s", category.strip().replace(' ', '-'), imageID);
        return saveImageAsJPG(filename);
    }

    @Override
    public boolean saveImageAsJPG(String filename) {
        if (!isInitialized()) return false;
        var imageBytes = getImageBytes();
        if (Objects.isNull(imageBytes)) return false;

        try {
            var prepSavePath = Path.of(RAND_CATEGORISED_SAVE_PATH + "/" + filename + ".jpg");
            Files.write(prepSavePath, imageBytes);
            return true;
        } catch (IOException exception) { return false; }
    }

    @Override
    public void reset() {
        this.cachedImage = null;
        this.stringJSON = null;
        this.category = null;
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() { return cachedImage.toMap().entrySet().iterator(); }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Object>> action) { Iterable.super.forEach(action); }

    @Override
    public String toString() {
        var clientLoaded = Objects.nonNull(client_id);
        var imageLoaded = Objects.nonNull(cachedImage);
        var categorySet = Objects.nonNull(category);
        return "Loaded {TOKEN=" + clientLoaded + ", IMAGE=" + imageLoaded + ", TAG=" + categorySet + "}";
    }
}
