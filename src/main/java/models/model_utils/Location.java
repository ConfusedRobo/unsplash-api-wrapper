package models.model_utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class Location {
    private String title;
    private String name;

    private String city;
    private String country;
    private JSONObject coordinates;

    public Location() {}

    public Location(String title, String name, String city, String country, String latitude, String longitude) {
        this.title = title;
        this.name = name;
        this.city = city;
        this.country = country;

        this.coordinates = new JSONObject();
        this.coordinates.put("latitude", latitude);
        this.coordinates.put("longitude", longitude);
    }

    public Location(String title, String name, String city, String country, @NotNull JSONObject coordinates) {
        this(title, name, city, country, coordinates.getString("latitude"), coordinates.getString("longitude"));
    }

    public void setAll(String title, String name, String city, String country, JSONObject coordinates) {
        this.title = title;
        this.name = name;
        this.city = city;
        this.country = country;
        this.coordinates = coordinates;
    }

    public void setAll(String title, String name, String city, String country, String latitude, String longitude) {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("latitude", latitude);
        jsonBuilder.put("longitude", longitude);
        setAll(title, name, city, country, jsonBuilder);
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
