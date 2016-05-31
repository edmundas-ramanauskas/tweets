package co.alttab.tweets;

/**
 * Created by edmundas on 16.5.31.
 */
public interface Serializer<T> {
    String serialize(T source);
}
