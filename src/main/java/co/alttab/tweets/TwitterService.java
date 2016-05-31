package co.alttab.tweets;

import twitter4j.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by edmundas on 16.5.30.
 */
public class TwitterService {

    private static final int DEFAULT_PAGE_SIZE = 100;

    private final String user;
    private final Twitter twitter = new TwitterFactory().getInstance();

    private List<Tweet> statuses;

    public TwitterService(String user) {
        this.user = user;
    }

    public TwitterService preload() throws TwitterException {
        statuses = loadAllStatuses();
        return this;
    }

    public List<Tweet> getAllStatuses() throws TwitterException {
        if(statuses != null) return statuses;
        return loadAllStatuses();
    }

    public List<Tweet> getAllStatusesFiltered(Predicate<Tweet> predicate) throws TwitterException {
        return getAllStatuses().stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Tweet> getStatuses() throws TwitterException {
        return getStatuses(1);
    }

    public List<Tweet> getStatuses(int page) throws TwitterException {
        if(statuses != null)
            return statuses.subList((page - 1) * DEFAULT_PAGE_SIZE, page * DEFAULT_PAGE_SIZE);

        return twitter.getUserTimeline(user, new Paging(page, DEFAULT_PAGE_SIZE))
                .stream().map(this::mapper).collect(Collectors.toList());
    }

    private List<Tweet> loadAllStatuses() throws TwitterException {
        List<Tweet> allStatuses = new ArrayList<>();
        int page = 1;
        List<Tweet> statuses = getStatuses(page);
        while(statuses.size() > 0) {
            allStatuses.addAll(statuses);
            statuses = getStatuses(++page);
        }
        return allStatuses;
    }

    private Tweet mapper(Status status) {
        List<Tweet.Media> mediaList = Arrays.stream(status.getMediaEntities())
                .map(mediaEntity -> new Tweet.Media(mediaEntity.getType(), mediaEntity.getMediaURL()))
                .collect(Collectors.toList());
        return new Tweet(status.getText(), mediaList);
    }
}
