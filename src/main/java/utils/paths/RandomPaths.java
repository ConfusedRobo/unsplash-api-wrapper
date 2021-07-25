package utils.paths;

import annotations.Author;
import utils.UnsplashRandomCategorised;
import utils.UnsplashRandomFixed;

import static utils.paths.TopPaths.RESOURCE_PATH;

/**
 * This interface is a list of all of the save paths that has been set by default and
 * it also has all of the paths that are being utilised in the {@code UnsplashRandomCategory}
 * class.
 *
 * @author ConfusedRobo
 * @see TopPaths
 */
@Author(
        author = "ConfusedRobo",
        creation = "30-06-2021",
        profile = "https://github.com/ConfusedRobo"
)
public interface RandomPaths {
    /**
     * Path to the cache directory where all of the {@link UnsplashRandomFixed}
     * and {@link UnsplashRandomCategorised} class's json files will be saved
     */
    String RAND_JSON_CACHE_SAVE_PATH = RESOURCE_PATH + "/jsoncaches/random";
    /**
     * Path to the random folder where all of the {@link UnsplashRandomFixed}
     * and {@link UnsplashRandomCategorised} class's images files will be saved
     */
    String RAND_IMAGE_SAVE_PATH = RESOURCE_PATH + "/random";
    /**
     * Path to the fixed folder where all of the images from {@link UnsplashRandomFixed}
     * will be saved
     */
    String RAND_IMAGE_FIXED_SAVE_PATH = RAND_IMAGE_SAVE_PATH + "/fixed";
    /**
     * Path to the fixed folder where all of the images from {@link UnsplashRandomCategorised}
     * will be saved
     */
    String RAND_CATEGORISED_SAVE_PATH = RAND_IMAGE_SAVE_PATH + "/categorised";
}