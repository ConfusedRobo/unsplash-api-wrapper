package demos;

import models.model_utils.Location;

public class ParseLocation {

    public static void main(String... args) {
        var location = new Location(null, null, null, null, null, null);
        System.out.println(location.packLocJSON().toString(2));
    }
}
