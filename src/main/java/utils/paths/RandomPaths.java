package utils.paths;

import annotations.Author;

import static utils.paths.TopPaths.RESOURCE_PATH;

/**
 *
 */
@Author(
        author = "ConfusedRobo",
        creation = "30-06-2021",
        profile = "https://github.com/ConfusedRobo"
)
public interface RandomPaths {
    String RAND_JSON_CACHE_SAVE_PATH = RESOURCE_PATH + "/jsoncaches/random";
    String RAND_IMAGE_SAVE_PATH = RESOURCE_PATH + "/random";
    String RAND_IMAGE_FIXED_SAVE_PATH = RAND_IMAGE_SAVE_PATH + "/fixed";
    String RAND_CATEGORISED_SAVE_PATH = RAND_IMAGE_SAVE_PATH + "/categorised";
}