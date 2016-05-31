package co.alttab.tweets;

import twitter4j.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by edmundas on 16.5.30.
 */
public class Application {

    private static final String DEFAULT_ACCOUNT = "katyperry";
    private static final String DEFAULT_IMAGES_PATH = "images";
    private static final String DEFAULT_JSON_FILE_PATH = "tweets.json";
    private static final String DEFAULT_XML_FILE_PATH = "tweets.xml";

    public static void main(String[] args) throws TwitterException {
        new Application().work();
    }

    private void work() throws TwitterException {
        TwitterService twitterService = new TwitterService(DEFAULT_ACCOUNT).preload();
        Serializer jsonSerializer = new JsonSerializer();
        Serializer xmlSerializer = new XmlSerializer(Account.class);
        PersistenceService persistenceService = new PersistenceService();

        List<Tweet> allStatuses = twitterService.getAllStatuses();
        List<Tweet> statusesWithImages = twitterService.getAllStatusesFiltered(
                tweet -> tweet.getEntities().stream()
                        .filter(media -> media.getType().equals("photo"))
                        .collect(Collectors.toList()).size() > 0
        );

        System.out.println(String.format("Total amount of tweets loaded: %d", allStatuses.size()));
        System.out.println(String.format("Total amount of tweets with images loaded: %d", statusesWithImages.size()));

        Account account = new Account(DEFAULT_ACCOUNT, allStatuses);

        String json = jsonSerializer.serialize(account);
        File jsonFile = Paths.get(DEFAULT_JSON_FILE_PATH).toFile();

        String xml = xmlSerializer.serialize(account);
        File xmlFile = Paths.get(DEFAULT_XML_FILE_PATH).toFile();

        System.out.println(jsonFile.getAbsolutePath());
        System.out.println(xmlFile.getAbsolutePath());

        persistenceService.saveToFile(jsonFile, json);
        persistenceService.saveToFile(xmlFile, xml);

        statusesWithImages.stream().forEach(tweet ->
            tweet.getEntities().stream().forEach(media -> {
                try {
                    URL url = new URL(media.getLink());
                    File file = Paths.get(DEFAULT_IMAGES_PATH, url.getFile()).toFile();
                    persistenceService.saveToFileFromUrl(url, file);
                    System.out.println(file.getAbsoluteFile());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            })
        );
    }
}
