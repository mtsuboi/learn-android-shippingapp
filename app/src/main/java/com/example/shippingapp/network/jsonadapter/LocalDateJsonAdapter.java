package com.example.shippingapp.network.jsonadapter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateJsonAdapter extends JsonAdapter<LocalDate> {
    @javax.annotation.Nullable
    @Override
    public LocalDate fromJson(JsonReader reader) throws IOException {
        if(reader.peek()== JsonReader.Token.NULL) {
            reader.nextNull();
            return null;
        } else {
            String value = reader.nextString();
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    @Override
    public void toJson(JsonWriter writer, @javax.annotation.Nullable LocalDate value) throws IOException {
        if(value == null) {
            writer.value((String) null);
        } else {
            writer.value(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
}
