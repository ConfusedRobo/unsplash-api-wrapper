package models;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQueries;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This class will parse all of the dates from the image JSON file. Note that the JSON file will
 * contain date keys like {@code created_at, updated_at, promoted_at}, etc. Also, the unsplash API uses
 * the <a href="https://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a> format.<br/>
 * An example of the JSON fragment that'll contain the date string:<br/>
 * <pre>{@code "user": {
 *     "id": "1233435",
 *     "updated_at": "2021-07-01T02:24:39-04:00",
 *     "username": "johnn112",
 *     "name": "John Doe",
 *     "first_name": "John",
 *     "last_name": "Doe"
 * }
 * }</pre>
 *
 * @see Iterable
 * @see Serializable
 * @see JSONObject
 */
public class ImageDateTime implements Serializable, Iterable<Map.Entry<String, Object>> {
    /**
     * The raw string that should follow the {@link DateTimeFormatter#ISO_DATE_TIME} format.
     *
     * @see DateTimeFormatter
     * @see DateTimeFormatter#ISO_DATE_TIME
     */
    private final String dateSource;

    /**
     * The date part that will be extracted from the {@link #dateSource} after parsing.
     *
     * @see LocalDate
     */
    private final LocalDate date;
    /**
     * The time part that will be extracted from the {@link #dateSource} after parsing.
     *
     * @see LocalTime
     */
    private final LocalTime time;

    /**
     * The chronology part that will be extracted from the {@link #dateSource} after parsing.
     *
     * @see Chronology
     */
    private final Chronology chronology;
    /**
     * The zone offset part that will be extracted from the {@link #dateSource} after parsing.
     *
     * @see ZoneOffset
     */
    private final ZoneOffset offset;
    /**
     * The zone id part that will be extracted from the {@link #dateSource} after parsing.
     *
     * @see ZoneId
     */
    private final ZoneId zone;

    /**
     * The serial version number field that will assist the JVM to correctly
     * cast/parse the object.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * A constructor, that parses all of the necessary information about the raw date string
     * right when an instance is created. Note that it isn't reusable i.e., You'd need to declare
     * another instance if you want to parse another date source string.
     *
     * @param dateSource the dataSource that needs to be parsed
     * @see DateTimeFormatter#parse(CharSequence)
     * @see TemporalQueries
     */
    public ImageDateTime(String dateSource) {
        var accessor = DateTimeFormatter.ISO_DATE_TIME.parse(dateSource);
        this.dateSource = dateSource;

        this.date = accessor.query(TemporalQueries.localDate());
        this.time = accessor.query(TemporalQueries.localTime());
        this.chronology = accessor.query(TemporalQueries.chronology());
        this.offset = accessor.query(TemporalQueries.offset());
        this.zone = accessor.query(TemporalQueries.zone());
    }

    /**
     * Getter for {@link #date}, extracting the date fragment out of the raw date string
     *
     * @return a {@link LocalDate} instance that will contain the date fragment.
     */
    public LocalDate date() { return date; }

    /**
     * Getter for {@link #time}, extracting the time fragment out of the raw date string
     *
     * @return a {@link LocalTime} instance that will contain the time fragment
     */
    public LocalTime time() { return time; }

    /**
     * Getter for {@link #chronology}, extracting the chronology fragment out of the raw date string
     *
     * @return a {@link Chronology} instance that will contain the chronology fragment
     */
    public Chronology chronology() { return chronology; }

    /**
     * Getter for {@link #offset}, extracting the zone offset fragment out of the raw date string
     *
     * @return a {@link ZoneOffset} instance that will contain the offset fragment
     */
    public ZoneOffset offset() { return offset; }

    /**
     * Getter for {@link #zone}, extracting the zone id fragment out of the date source string
     *
     * @return a {@link ZoneId} instance that will contain the zone id fragment
     */
    public ZoneId zone() { return zone; }

    /**
     * Getter for {@link #dateSource} string
     *
     * @return a {@code String} that'll contain the raw date string
     */
    public String source() { return dateSource; }

    /**
     * This method will check for nullity for all objects if the said object turns out to be
     * {@code null} then {@link JSONObject#NULL} will be returned, otherwise that same object
     * will be returned
     *
     * @param object that will be checked if it's {@code null} or, not
     * @return an object that is either the same as param or, {@link JSONObject#NULL} if the param object
     * turns out to be {@code null}
     * @see JSONObject#NULL
     */
    public Object escapeNull(Object object) { return object == null ? JSONObject.NULL : object; }

    /**
     * Packs {@link #date}, {@link #time}, {@link #chronology}, {@link #offset} and {@link #zone} into
     * a {@link JSONObject}.
     *
     * @return a {@link JSONObject} instance
     */
    public JSONObject toJSON() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("date", escapeNull(date));
        jsonBuilder.put("time", escapeNull(time));
        jsonBuilder.put("chronology", escapeNull(chronology));
        jsonBuilder.put("offset", escapeNull(offset));
        jsonBuilder.put("zone", escapeNull(zone));
        return jsonBuilder;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() { return toJSON().toMap().entrySet().iterator(); }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side-effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super Map.Entry<String, Object>> action) { Iterable.super.forEach(action); }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * The default implementation creates an
     * <em><a href="../util/Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<Map.Entry<String, Object>> spliterator() { return Iterable.super.spliterator(); }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() { return toJSON().toString(2); }
}
