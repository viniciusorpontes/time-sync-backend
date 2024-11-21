package br.com.timesync.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateUtil {

    public static String formatarData(LocalDateTime data) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(formatter);
    }

}
