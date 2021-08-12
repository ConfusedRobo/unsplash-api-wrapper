package demos;

import models.CameraInfo;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CamInfoMain {

    public static void main(String... args) throws IOException {
        var decoded = Files.readString(Path.of("resources/temp/exif-fragment.json"));
        System.out.println(decoded);

        var json = new JSONObject(decoded);
        var exif = new CameraInfo(
                json.getString(CameraInfo.EXIFKeys.MAKE),
                json.getString(CameraInfo.EXIFKeys.MODEL),
                json.getString(CameraInfo.EXIFKeys.EXPOSURE_TIME),
                json.getString(CameraInfo.EXIFKeys.APERTURE),
                json.getString(CameraInfo.EXIFKeys.FOCAL_LENGTH),
                json.getInt(CameraInfo.EXIFKeys.ISO)
        );

        System.out.println(exif.toJSON());
        System.out.println(exif.toJSON().toString().equals(json.toString()));
    }
}
