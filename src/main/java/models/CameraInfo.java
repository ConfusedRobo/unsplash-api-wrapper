package models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class represents 
 */
public class CameraInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The brand of the equipment that was used for taking the image
     */
    public final String make;
    /**
     *
     */
    public final String model;
    /**
     *
     */
    public final String exposureTime;
    /**
     *
     */
    public final String aperture;
    /**
     *
     */
    public final String focalLength;
    /**
     *
     */
    public final Integer iso;

    public CameraInfo(String make, String model, String exposureTime, String aperture, String focalLength, int iso) {
        this.make = make;
        this.model = model;
        this.exposureTime = exposureTime;
        this.aperture = aperture;
        this.focalLength = focalLength;
        this.iso = iso;
    }

    private Object escapeNull(Object object) { return object == null ? JSONObject.NULL : object; }

    public JSONObject toJSON() {
        final var jsonBuilder = new JSONObject();
        jsonBuilder.put(EXIFKeys.MAKE, escapeNull(make));
        jsonBuilder.put(EXIFKeys.MODEL, escapeNull(model));
        jsonBuilder.put(EXIFKeys.EXPOSURE_TIME, escapeNull(exposureTime));
        jsonBuilder.put(EXIFKeys.APERTURE, escapeNull(aperture));
        jsonBuilder.put(EXIFKeys.FOCAL_LENGTH, escapeNull(focalLength));
        jsonBuilder.put(EXIFKeys.ISO, escapeNull(iso));
        return jsonBuilder;
    }

    public static final class EXIFKeys {
        public static final String MAKE = "make";
        public static final String MODEL = "model";
        public static final String EXPOSURE_TIME = "exposure_time";
        public static final String APERTURE = "aperture";
        public static final String FOCAL_LENGTH = "focal_length";
        public static final String ISO = "iso";

        @Contract(pure = true)
        public static @NotNull @Unmodifiable List<String> getAllKeys() {
            return List.of(MAKE, MODEL, EXPOSURE_TIME, APERTURE, FOCAL_LENGTH, ISO);
        }
    }
}
