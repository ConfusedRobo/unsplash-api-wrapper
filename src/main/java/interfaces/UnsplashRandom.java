package interfaces;

import java.io.Serializable;

@SuppressWarnings("UnusedReturnValue")
public abstract class UnsplashRandom implements Serializable {

    public static final String API_LINK = "https://api.unsplash.com/photos/random/?client_id=API_KEY";


    public abstract boolean init();

    public abstract boolean saveImageAsJSONFile();

    public abstract String downloadLink();

    public abstract boolean saveImageAsJPG();

    public abstract boolean saveImageAsJPG(String filename);

    public abstract void reset();
}
