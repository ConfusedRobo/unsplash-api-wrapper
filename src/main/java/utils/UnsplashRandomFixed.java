package utils;

import interfaces.UnsplashRandom;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import utils.paths.RandomPaths;

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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.net.http.HttpResponse.BodyHandlers;

@SuppressWarnings("UnusedReturnValue")
public class UnsplashRandomFixed extends UnsplashRandom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private JSONObject cachedImage;
    private String stringJSON;

    public String clientID;

    private void loadToken() { this.clientID = Dotenv.configure().filename(".env").load().get("API_KEY"); }

    public boolean init() {
        loadToken();
        if (Objects.isNull(clientID)) throw new NullPointerException("clientID is null");

        var prepared = API_LINK.replace("API_KEY", clientID);
        var request = HttpRequest.newBuilder(URI.create(prepared)).build();
        var client = HttpClient.newHttpClient();
        try {
            var builder = new StringBuilder(100);
            client.sendAsync(request, BodyHandlers.ofString())
                  .thenApply(HttpResponse::body)
                  .thenAccept(builder::append).get(2, TimeUnit.SECONDS);
            this.stringJSON = builder.toString();
            this.cachedImage = new JSONObject(builder.toString());
            return true;
        } catch (InterruptedException | TimeoutException | ExecutionException exception) { return false; }
    }

    public Map<String, Integer> getImageDimensions() {
        if (Objects.isNull(cachedImage)) return null;
        var width = cachedImage.getInt("width");
        var height = cachedImage.getInt("height");
        return Map.of("width", width, "height", height);
    }

    @Override
    public boolean saveImageAsJSONFile() {
        if (Objects.isNull(cachedImage)) return false;
        try {
            var prepPath = Path.of(RandomPaths.RAND_JSON_CACHE_SAVE_PATH + "/" + "sample.json");
            Files.deleteIfExists(prepPath);
            Files.writeString(prepPath, stringJSON);
            return true;
        } catch (IOException exception) { return false; }
    }

    @Override
    public String downloadLink() {
        if (Objects.isNull(cachedImage)) return null;
        return cachedImage.getJSONObject("links").getString("download");
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

    @Override
    public boolean saveImageAsJPG(String filename) {
        if (Objects.isNull(cachedImage)) return false;
        var imageBytes = getImageBytes();
        if (Objects.isNull(imageBytes)) return false;
        try {
            var prepPath = Path.of(RandomPaths.RAND_IMAGE_FIXED_SAVE_PATH + "/" + filename + ".jpg");
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
    public String toString() {
        var clientLoaded = Objects.nonNull(clientID);
        var imageLoaded = Objects.nonNull(cachedImage);
        return "Loaded {TOKEN=" + clientLoaded + ", IMAGE=" + imageLoaded + " }";
    }
}
