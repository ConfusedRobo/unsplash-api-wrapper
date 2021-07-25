package demos;

import utils.UnsplashRandomCategorised;

import static java.lang.System.out;

public class URCMain {

    public static void main(String... args) {
        var categorised = new UnsplashRandomCategorised("cats");
        out.println(categorised.init());
        out.println(categorised.downloadLink());

        out.println(categorised.init("robot"));
        out.println(categorised.downloadLink());
    }
}
