package co.alttab.tweets;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by edmundas on 16.5.31.
 */
public class PersistenceService {

    public void saveToFile(File file, String data) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }

    public void saveToFileFromUrl(URL url, File file) throws IOException {
        file.getParentFile().mkdirs();
        URLConnection connection = url.openConnection();
        InputStream input = connection.getInputStream();
        byte[] buffer = new byte[4096];
        int n = -1;

        OutputStream output = new FileOutputStream(file);
        while ( (n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        output.close();
        input.close();
    }
}
