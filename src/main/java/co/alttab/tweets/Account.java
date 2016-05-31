package co.alttab.tweets;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by edmundas on 16.5.31.
 */
@XmlRootElement
public class Account {
    private String name;
    private List<Tweet> tweets;

    public Account() {

    }

    public Account(String name, List<Tweet> tweets) {
        this.name = name;
        this.tweets = tweets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name="tweets")
    @XmlElement(name="tweet")
    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
