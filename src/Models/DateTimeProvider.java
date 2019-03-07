package Models;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeProvider {

    public static Time getSqlTime() {
        return new Time(Calendar.getInstance().getTime().getTime());
    }

    public static Date getSqlDate() {
        return new Date(Calendar.getInstance().getTime().getTime());
    }

    public static java.util.Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String formatDate(java.util.Date date, Locale locale){
        String formatted = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, locale)
                .format(date);
        return formatted;
    }
}
