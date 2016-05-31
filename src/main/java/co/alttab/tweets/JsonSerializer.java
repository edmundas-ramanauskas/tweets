package co.alttab.tweets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by edmundas on 16.5.31.
 */
public class JsonSerializer implements Serializer<Object> {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String serialize(Object source) {
        return gson.toJson(source);
    }
}
