package soft.rodi38.jekyllmate.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class BlogPostService {
    private static final String POSTS_DIRECTORY = System.getenv("JEKYLL_POST_LOCATION");
    private static final String ASSETS_DIRECTORY = System.getenv("JEKYLL_ASSETS_LOCATION");

    public void createPost(String title, String content, String category) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String filename = date + "-" + title.replace(" ", "-") + ".markdown";

        if (content == null) {
            return;
        }
        System.out.println(POSTS_DIRECTORY);

        try {

            Path filePath = Paths.get(POSTS_DIRECTORY, filename);
            BufferedWriter writer = Files.newBufferedWriter(filePath);
            writer.write("---\n");
            writer.write("layout: post\n");
            writer.write("title:  \"" + title + "\"\n");
            writer.write("date: " + date + "\n");
            writer.write("categories: " + category + "\n");
            writer.write("---\n");


            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
