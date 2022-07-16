package com.eldorado.eldoradoservice_pricing.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateFormat {
    private DateFormat(){
        throw new IllegalStateException("Static class");
    }
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;

    public static String format(OffsetDateTime offsetDateTime){
        return fmt.format(offsetDateTime.withOffsetSameLocal(ZoneOffset.UTC));
    }

    public static OffsetDateTime parse(String datetime){
        return OffsetDateTime.parse(datetime);
    }
}
