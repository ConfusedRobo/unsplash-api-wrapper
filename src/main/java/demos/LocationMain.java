package demos;

import models.model_utils.Location;

public class LocationMain {

    public static void main(String... args) {
        var location = new Location(null, null, null, null, null, null);
        System.out.println(location.toJSON().toString(2));
    }
}
