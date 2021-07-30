package utils.paths;

import annotations.Author;

/**
 * This interface is a collection of all the main/global paths that are and
 * will be used throughout the project.
 *
 * @author ConfusedRobo
 */
@Author(
        author = "ConfusedRobo",
        creation = "Monday, 19 July, 2021, 05:34:35 PM",
        profile = "https://github.com/heretickeymaker"
)
public interface TopPaths {
    /**
     * This path where all the resources like logs, temp, json files, images are stored
     */
    String RESOURCE_PATH = "resources";
    /**
     * The path to the .env file where the API KEY is stored
     */
    String ENV_FILEPATH = ".env";
    /**
     * The path to logs directory where all the logs are stored
     */
    String LOG_PATH = RESOURCE_PATH + "/logs";
    /**
     * The path to temp directory where all the temporary files are and
     * is recommended to be stored
     */
    String TEMP_PATH = RESOURCE_PATH + "/temp";
}
