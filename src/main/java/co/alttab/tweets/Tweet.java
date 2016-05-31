package co.alttab.tweets;

import java.util.List;

/**
 * Created by edmundas on 16.5.31.
 */
public class Tweet {
    private String text;
    private List<Media> entities;

    public Tweet() {

    }

    public Tweet(String text, List<Media> entities) {
        this.text = text;
        this.entities = entities;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Media> getEntities() {
        return entities;
    }

    public void setEntities(List<Media> entities) {
        this.entities = entities;
    }

    public static class Media {
        private String type;
        private String link;

        public Media() {

        }

        public Media(String type, String link) {
            this.type = type;
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
