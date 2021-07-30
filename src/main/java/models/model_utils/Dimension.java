package models.model_utils;

import annotations.Author;

import java.io.Serial;
import java.io.Serializable;

/**
 * A model class that parses the width and height keys into a single object, mainly for
 * semantic reasons, it also supports serialization.
 *
 * @see Serializable
 *
 * @author ConfusedRobo
 */
@Author(
        author = "ConfusedRobo",
        creation = "Thursday, 29 July, 2021, 04:46:50 AM",
        profile = "https://github.com/heretickeymaker"
)
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
     * serialize and deserialize the object
     */
    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * The default constructor
     */
    public Dimension() {}

    /**
     * Constructor, that assigns the {@link Dimension#height} and {@link #width} fields which should be parsed
     * from the fetched JSON file
     *
     * @param width that assigns the {@link Dimension#width} field
     * @param height that assigns the {@link Dimension#height} field
     */
    public Dimension(int width, int height) { this.width = width; this.height = height; }
}
