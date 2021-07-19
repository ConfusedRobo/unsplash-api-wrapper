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
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class UnsplashImageDateTimeData implements Serializable, Iterable<Map.Entry<String, Object>> {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private TemporalAccessor accessor;
    private String dateSource;

    private LocalDate date;
    private LocalTime time;

    private Chronology chronology;
    private ZoneOffset offset;
    private ZoneId zone;

    @Serial
    private static final long serialVersionUID = 812346812L;

    public UnsplashImageDateTimeData(String dateSource) {
        this.accessor = DATE_TIME_FORMATTER.parse(dateSource);
        this.dateSource = dateSource;
        parseDate();
    }

    private void parseDate() {
        this.date = accessor.query(TemporalQueries.localDate());
        this.time = accessor.query(TemporalQueries.localTime());
        this.chronology = accessor.query(TemporalQueries.chronology());
        this.offset = accessor.query(TemporalQueries.offset());
        this.zone = accessor.query(TemporalQueries.zone());
    }

    public void changeDateSource(String newDateSource) {
        this.accessor = DATE_TIME_FORMATTER.parse(newDateSource);
        this.dateSource = newDateSource;
        parseDate();
    }

    public TemporalAccessor getAccessor() { return accessor; }

    public LocalDate getDate() { return date; }

    public LocalTime getTime() { return time; }

    public Chronology getChronology() { return chronology; }

    public ZoneOffset getOffset() { return offset; }

    public ZoneId getZone() { return zone; }

    public JSONObject packDTasJSONNative() {
        var jsonBuilder = new JSONObject();
        jsonBuilder.put("date", date);
        jsonBuilder.put("time", time);
        jsonBuilder.put("chronology", chronology);
        jsonBuilder.put("offset", offset);
        jsonBuilder.put("zone", zone);
        return jsonBuilder;
    }

    public JSONObject packDTasJSONStringValued() {
        var jsonNative = packDTasJSONNative();
        var jsonBuilder = new JSONObject();
        jsonNative.toMap().forEach((key, value) -> jsonBuilder.put(key, value.toString()));
        return jsonBuilder;
    }

    public String getDateSource() { return dateSource; }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return packDTasJSONNative().toMap().entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Object>> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, Object>> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    public String toString() {
        return "UnsplashImageDateTimeData{" +
               "accessor=" + accessor +
               ", dateSource='" + dateSource + '\'' +
               ", date=" + date +
               ", time=" + time +
               ", chronology=" + chronology +
               ", offset=" + offset +
               ", zone=" + zone +
               '}';
    }
}
