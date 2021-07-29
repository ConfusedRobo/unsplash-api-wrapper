package models.model_utils;

import java.io.Serial;
import java.io.Serializable;

/**
 * A model class that parses the width and height keys into a single object, mainly for
 * semantic reasons, it also supports serialization.
 *
 * @see Serializable
 */
public final class Dimension implements Serializable {
    /**
     * The width of the image that will be specified in the fetched JSON file
     */
    public int width;
    /**
     * The height of the image that will be specified in the fetched JSON file
     */
    public int height;

    /**
     * The serial version number field that will assist the JVM to correctly
     * serialize/deserialize the object
     */
    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * Constructor, that assigns the {@link #height} and {@link #width} fields which should be parsed
     * from the fetched JSON file
     *
     * @param width that assigns the {@link #width} field
     * @param height that assigns the {@link #height} field
     */
    public Dimension(int width, int height) { this.width = width; this.height = height; }

    /**
     * The default constructor
     */
    public Dimension() {}
}
