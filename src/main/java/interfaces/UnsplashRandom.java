package interfaces;

import java.io.Serializable;

@SuppressWarnings("ALL")
public interface UnsplashRandom extends Serializable {

    String API_LINK = "https://api.unsplash.com/photos/random/?client_id=API_KEY";
    String LINK_SUBPART_QUERY = "&query=CATEGORY";

    boolean init();

    boolean saveImageAsJSONFile();

    String downloadLink();

    boolean saveImageAsJPG();

    boolean saveImageAsJPG(String filename);

    void reset();
}
