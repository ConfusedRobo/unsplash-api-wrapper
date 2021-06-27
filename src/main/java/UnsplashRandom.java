import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;


@SuppressWarnings("UnusedReturnValue")
public class UnsplashRandom {
    private JSONObject cachedImage;
    private String stringJSON;
    private String clientID;

    public void loadToken() {
        this.clientID = Dotenv.configure()
                              .filename(".env")
                              .load()
                              .get("API_KEY");
    }

    public boolean init() {
        if (Objects.isNull(clientID)) throw new NullPointerException("clientID is null");
        var prepURL = "https://api.unsplash.com/photos/random/?client_id=" + clientID;
        try(var stream = new URL(prepURL).openStream()) {
            this.stringJSON = new String(stream.readAllBytes());
            this.cachedImage = new JSONObject(stringJSON);
            return true;
        } catch (IOException exception) { return false; }
    }

    public boolean saveImageAsJSONFile() {
        if (Objects.isNull(cachedImage)) return false;

        try {
            Files.deleteIfExists(Path.of("cache.json"));
            Files.writeString(Path.of("cache.json"), stringJSON);
            return true;
        } catch (IOException exception) { return false; }
    }

    public String getDownloadLink() {
        if (Objects.isNull(cachedImage)) return null;
        return cachedImage.getJSONObject("links").getString("download");
    }

    private byte[] getImageBytes() {
        try (var stream = new URL(getDownloadLink()).openStream()) {
            return stream.readAllBytes();
        } catch (IOException exception) { return null; }
    }

    public Map<String, Integer> getImageDimensions() {
        if (Objects.isNull(cachedImage)) return null;

        var width = cachedImage.getInt("width");
        var height = cachedImage.getInt("height");
        return Map.of("width", width, "height", height);
    }

    public boolean saveImageAsJPG() {
        var imageID = cachedImage.getString("id");
        var filename = String.format("%s", imageID);
        return saveImageAsJPG(filename);
    }

    public boolean saveImageAsJPG(String filename) {
        if (Objects.isNull(cachedImage)) return false;

        var imageBytes = getImageBytes();
        if (Objects.isNull(imageBytes)) return false;
        try { Files.write(Path.of(filename + ".jpg"), imageBytes); return true; }
        catch (IOException exception) { return false; }
    }

    public void reset() {
        cachedImage = null;
        stringJSON = null;
    }
}
