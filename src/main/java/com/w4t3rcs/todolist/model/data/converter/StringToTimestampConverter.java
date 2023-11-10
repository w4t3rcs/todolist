package com.w4t3rcs.todolist.model.data.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToTimestampConverter implements Converter<String, Timestamp> {
    @Override
    public Timestamp convert(String source) {
        if (source.isBlank()) return null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date parsedDate = dateFormat.parse(source);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
