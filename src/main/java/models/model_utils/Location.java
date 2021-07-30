package models.model_utils;

import annotations.Author;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;

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
 *
 * @author ConfusedRobo
 */
@Author(
        author = "ConfusedRobo",
        creation = "02-07-2021",
        profile = "https://github.com/heretickeymaker"
)
public class Location implements Serializable {
    /**
     * The serial version number field that will assist the JVM to correctly
     * serialize and deserialize the object
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The title of the location the photo was taken in (if any)
     */
    private String title;
    /**
     * Name of the location the photo was taken in (if any)
     */
    private String name;
    /**
     * City of the location the photograph was taken in (if applicable)
     */
    private String city;
    /**
     * Country of the location, this photograph was taken at (if applicable)
     */
    private String country;
    /**
     * Coordinates of the exact location that the photograph was taken in
     */
    private JSONObject coordinates;

    /**
     * The default constructor that has been created so that all field could be
     * allowed to kept null.
     */
    public Location() {}

    /**
     * Sets the {@link Location#title} field.
     *
     * @param title the title of the location the photograph was taken in.
     */
    public Location(String title) {this.title = title;}

    /**
     * A convenience method for setting {@link Location#title}
     * and {@link Location#city} fields all in one go.
     *
     * @param title the title of the location the photo graph was taken in.
     * @param city  the city where the photograph was taken at.
     */
    public Location(String title, String city) {
        this.title = title;
        this.city = city;
    }

    /**
     * A convenience constructor that sets all the fields separately, especially, the latitude and
     * longitude part which takes those in separately as parameters and then packs them in a
     * {@link JSONObject} and then sets the coordinate field.
     *
     * @param title     the title of the location where the photo was taken in.
     * @param name      the name where the photo was taken in.
     * @param city      the city where the photo was taken in.
     * @param country   the country where the photo was taken in.
     * @param latitude  the latitude of the exact location the photo was taken at.
     * @param longitude the longitude of the exact location the photo was taken at.
     * @see JSONObject
     */
    public Location(String title, String name, String city, String country, String latitude, String longitude) {
        this.title = title;
        this.name = name;
        this.city = city;
        this.country = country;

        this.coordinates = new JSONObject();
        this.coordinates.put("latitude", escapeNull(latitude));
        this.coordinates.put("longitude", escapeNull(longitude));
    }

    /**
     * A convenience constructor that takes in all the field value as is more precisely it
     * takes in the {@link Location#coordinates} field as a {@link JSONObject} and not as strings
     * like its supertype.
     *
     * @param title       the title of the location where the photo was taken in.
     * @param name        the name where the photo was taken in.
     * @param city        the city where the photo was taken in.
     * @param country     the country where the photo was taken in.
     * @param coordinates the latitude and longitude of the exact location that the photograph
     *                    was taken in.
     * @see JSONObject
     */
    public Location(String title, String name, String city, String country, @NotNull JSONObject coordinates) {
        this(title, name, city, country, coordinates.getString("latitude"), coordinates.getString("longitude"));
    }

    /**
     * A simple utility method that escapes a {@code null} value/type. Note that the {@link org.json} API doesn't
     * allow null to be a type to be a value in {@link JSONObject} so to replace that, the static field called
     * {@link JSONObject#NULL} is used and this method just replaces and return {@link JSONObject#NULL} if it
     * encounters a {@code null} object/type
     *
     * @param value and value/object/type that is changed to {@link JSONObject#NULL} if {@code null} is passed.
     * @return {@link JSONObject#NULL} value that denotes null/None/Nothing/Empty in a JSON file.
     * @see JSONObject
     */
    private Object escapeNull(Object value) {return isNull(value) ? JSONObject.NULL : value;}

    /**
     * A convenience method that takes in all the field value as is more precisely it
     * takes in the {@link Location#coordinates} field as a {@link JSONObject} and not as strings
     * like its supertype.
     *
     * @param title       the title of the location where the photo was taken in.
     * @param name        the name where the photo was taken in.
     * @param city        the city where the photo was taken in.
     * @param country     the country where the photo was taken in.
     * @param coordinates the latitude and longitude of the exact location that the photograph
     *                    was taken in.
     * @see JSONObject
     */
    public void setAll(String title, String name, String city, String country, JSONObject coordinates) {
        this.title = title;
        this.name = name;
        this.city = city;
        this.country = country;
        this.coordinates = coordinates;
    }

    /**
     * A convenience method that sets all the fields separately, especially, the latitude and
     * longitude part which takes those in separately as parameters and then packs them in a
     * {@link JSONObject} and then sets the coordinate field.
     *
     * @param title     the title of the location where the photo was taken in.
     * @param name      the name where the photo was taken in.
     * @param city      the city where the photo was taken in.
     * @param country   the country where the photo was taken in.
     * @param latitude  the latitude of the exact location the photo was taken at.
     * @param longitude the longitude of the exact location the photo was taken at.
     * @see JSONObject
     */
    public void setAll(String title, String name, String city, String country, String latitude, String longitude) {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("latitude", escapeNull(latitude));
        jsonBuilder.put("longitude", escapeNull(longitude));
        setAll(title, name, city, country, jsonBuilder);
    }

    /**
     * Packs all the fields into a {@link JSONObject}.<br/>
     * The JSON structure will look like:
     * <pre>{@code
     * "location": {
     *   "title": null,
     *   "name": null,
     *   "city": null,
     *   "country": null,
     *   "position": {
     *     "latitude": null,
     *     "longitude": null
     *   }
     * }
     * }
     *
     * @return a {@link JSONObject}
     * @see JSONObject
     */
    public JSONObject toJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("title", escapeNull(title));
        jsonBuilder.put("name", escapeNull(name));
        jsonBuilder.put("city", escapeNull(city));
        jsonBuilder.put("country", escapeNull(country));
        jsonBuilder.put("position", coordinates);
        return jsonBuilder;
    }

    /**
     * Getter for the {@link Location#title} field.
     *
     * @return a {@link String}
     */
    public String getTitle() {return title;}

    /**
     * Setter for the {@link Location#title} field
     *
     * @param title the tile of the place the photo was taken in
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * Getter for the {@link Location#name} field.
     *
     * @return a {@link String}
     */
    public String getName() {return name;}

    /**
     * Setter for the {@link Location#name} field
     *
     * @param name the name of the location the photo was taken at.
     */
    public void setName(String name) {this.name = name;}

    /**
     * Getter for the name of the city the photo was taken at.
     *
     * @return a {@link String}
     */
    public String getCity() {return city;}

    /**
     * Setter for the {@link Location#city} field.
     *
     * @param city the name of the city where the photo was taken in.
     */
    public void setCity(String city) {this.city = city;}

    /**
     * Getter for the {@link Location#country} field.
     *
     * @return a {@link String}
     */
    public String getCountry() {return country;}

    /**
     * Setter for the {@link Location#country} field.
     *
     * @param country is the name of the country where the photo was taken in.
     */
    public void setCountry(String country) {this.country = country;}

    /**
     * Getter for the {@link Location#coordinates} field.
     *
     * @return a {@link String}
     */
    public JSONObject getCoordinates() {return coordinates;}

    /**
     * Setter for the {@link Location#coordinates} field.
     *
     * @param coordinates is the latitude and longitude value of the exact location where the
     *                    photo was taken in.
     */
    public void setCoordinates(JSONObject coordinates) {this.coordinates = coordinates;}

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object.
     * It basically calls the {@link Location#toJSON()} method which returns
     * a {@link JSONObject} and the {@link JSONObject#toString(int)} of that object is
     * printed, with an indent factor of 2 tabs.
     *
     * @return a {@link String}
     */
    @Override
    public String toString() {return toJSON().toString(2);}
}
