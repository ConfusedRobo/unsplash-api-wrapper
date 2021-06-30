package utils.paths;

import static utils.paths.TopLevelPaths.RESOURCE_PATH;

public interface RandomPaths {
    String RAND_JSON_CACHE_SAVE_PATH = RESOURCE_PATH + "/jsoncaches/random";
    String RAND_IMAGE_SAVE_PATH = RESOURCE_PATH + "/random";
    String RAND_IMAGE_FIXED_SAVE_PATH = RAND_IMAGE_SAVE_PATH + "/fixed";
    String RAND_CATEGORISED_SAVE_PATH = RAND_IMAGE_SAVE_PATH + "/categorised";
}