package utils.paths;

/**
 * This interface is a collection of all of the main/global paths that are and
 * will be used throughout the project.
 *
 * @author ConfusedRobo
 */
public interface TopPaths {
    /**
     * This path where all of the resources like logs, temp, json files, images are stored
     */
    String RESOURCE_PATH = "resources";
    /**
     * The path to the .env file where the API KEY is stored
     */
    String ENV_FILEPATH = ".env";
    /**
     * The path to logs directory where all of the logs are stored
     */
    String LOG_PATH = RESOURCE_PATH + "/logs";
    /**
     * The path to temp directory where all of the temporary files are and
     * is recommended to be stored
     */
    String TEMP_PATH = RESOURCE_PATH + "/temp";
}
