package demos;

import models.ImageDateTime;

import static java.lang.System.out;

public class DateTimeMain {

    public static void main(String... args) {
        var dateTime = new ImageDateTime("2021-06-09T02:16:37-04:00");
        out.println(dateTime);
        out.println(dateTime.date());
        out.println(dateTime.time());
        out.println(dateTime.offset());
        out.println(dateTime.chronology());
        out.println(dateTime.zone());
        out.println(dateTime.source());
        dateTime.forEach(item -> out.print(item + " "));
    }
}
