package utils;

import interfaces.UnsplashRandom;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import static utils.paths.RandomPaths.RAND_IMAGE_FIXED_SAVE_PATH;
import static utils.paths.RandomPaths.RAND_JSON_CACHE_SAVE_PATH;
import static utils.paths.StandalonePaths.*;

@SuppressWarnings("ALL")
public class UnsplashRandomFixed implements Serializable, Iterable<Map.Entry<String, Object>>, UnsplashRandom {
    @Serial
    private static final long serialVersionUID = 1L;

    private String client_id;

    private JSONObject cachedImage;
    private String stringJSON;

    private void loadToken() { this.client_id = Dotenv.configure().filename(ENV_FILEPATH).load().get("API_KEY"); }

    private boolean isInitialized() { return Objects.nonNull(stringJSON) && Objects.nonNull(cachedImage); }

    public boolean init() {
        loadToken();
        if (Objects.isNull(client_id)) throw new NullPointerException("client_ID is null");

        var prepURLString = API_LINK.replace("API_KEY", client_id);
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
        } catch (InterruptedException | TimeoutException | ExecutionException exception) { return false; }
    }

    public Map<String, Integer> getImageDimensions() {
        if (!isInitialized()) return null;
        var width = cachedImage.getInt("width");
        var height = cachedImage.getInt("height");
        return Map.of("width", width, "height", height);
    }

    @Override
    public boolean saveImageAsJSONFile() {
        if (!isInitialized()) return false;
        try {
            var prepPath = Path.of(RAND_JSON_CACHE_SAVE_PATH + "/" + "sample-fixed.json");
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

    @Nullable
    private byte[] getImageBytes() {
        try (var stream = new URL(downloadLink()).openStream()) {
            return stream.readAllBytes();
        } catch (IOException exception) { return null; }
    }

    @Override
    public boolean saveImageAsJPG() {
        var imageID = cachedImage.getString("id");
        var filename = String.format("%s", imageID);
        return saveImageAsJPG(filename);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public boolean saveImageAsJPG(String filename) {
        if (!isInitialized()) return false;
        var imageBytes = getImageBytes();
        if (Objects.isNull(imageBytes)) return false;

        try {
            var prepPath = Path.of(RAND_IMAGE_FIXED_SAVE_PATH + "/" + filename + ".jpg");
            Files.write(prepPath, imageBytes);
            return true;
        } catch (IOException exception) { return false; }
    }

    @Override
    public void reset() {
        cachedImage = null;
        stringJSON = null;
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Object>> action) { Iterable.super.forEach(action); }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() { return cachedImage.toMap().entrySet().iterator(); }

    @Override
    public String toString() {
        var clientLoaded = Objects.nonNull(client_id);
        var imageLoaded = Objects.nonNull(cachedImage);
        return "Loaded {TOKEN=" + clientLoaded + ", IMAGE=" + imageLoaded + "}";
    }
}
