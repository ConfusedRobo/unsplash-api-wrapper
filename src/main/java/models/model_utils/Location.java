package models.model_utils;

import annotations.Author;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONPropertyName;

import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * A model class that is made to parse the Unsplash JSON {@code KEY=user} to
 * this class. The parsing should be handled with care. And, it is recommended
 * to call the setters in the same order as in the JSON file. {@code Location}
 * class plays a crucial role in building up the full JSON file, if you need to
 * send a request to the server for posting an image, it is essential to build
 * a {@code Location} object first then have a method repackage the given fields
 * to a JSONObject for transfer.
 *
 * @see JSONObject
 */
@Author(
        author = "ConfusedRobo",
        creation = "02-07-2021",
        profile = "https://github.com/ConfusedRobo"
)
public class Location {
    private String title;
    private String name;

    private String city;
    private String country;
    private JSONObject coordinates;

    public Location() {}

    public Location(String title) { this.title = title; }

    public Location(String title, String city) {
        this.title = title;
        this.city = city;
    }

    public Location(String title, String name, String city, String country, String latitude, String longitude) {
        this.title = title;
        this.name = name;
        this.city = city;
        this.country = country;

        this.coordinates = new JSONObject();
        this.coordinates.put("latitude", escapeNull(latitude));
        this.coordinates.put("longitude", escapeNull(longitude));
    }

    public Location(String title, String name, String city, String country, @NotNull JSONObject coordinates) {
        this(title, name, city, country, coordinates.getString("latitude"), coordinates.getString("longitude"));
    }

    private Object escapeNull(Object value) { return isNull(value) ? JSONObject.NULL : value; }

    public void setAll(String title, String name, String city, String country, JSONObject coordinates) {
        this.title = title;
        this.name = name;
        this.city = city;
        this.country = country;
        this.coordinates = coordinates;
    }

    public void setAll(String title, String name, String city, String country, String latitude, String longitude) {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("latitude", escapeNull(latitude));
        jsonBuilder.put("longitude", escapeNull(longitude));
        setAll(title, name, city, country, jsonBuilder);
    }

    // "location": {
    //   "title": null,
    //   "name": null,
    //   "city": null,
    //   "country": null,
    //   "position": {
    //     "latitude": null,
    //     "longitude": null
    //   }
    // }
    public JSONObject packLocJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("title", escapeNull(title));
        jsonBuilder.put("name", escapeNull(name));
        jsonBuilder.put("city", escapeNull(city));
        jsonBuilder.put("country", escapeNull(country));
        jsonBuilder.put("position", coordinates);
        return jsonBuilder;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public JSONObject getCoordinates() { return coordinates; }

    public void setCoordinates(JSONObject coordinates) { this.coordinates = coordinates; }

    @Override
    public String toString() {
        return "Location{" +
               "title='" + title + '\'' +
               ", name='" + name + '\'' +
               ", city='" + city + '\'' +
               ", country='" + country + '\'' +
               ", coordinates=" + coordinates +
               '}';
    }
}
