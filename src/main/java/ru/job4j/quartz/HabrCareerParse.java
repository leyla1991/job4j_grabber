package ru.job4j.quartz;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) throws IOException {
            HabrCareerParse habrCareerParse = new HabrCareerParse(new HabrCareerDateTimeParser());
            List<Post> list = habrCareerParse.list(PAGE_LINK);
            for (Post post : list) {
                System.out.println(post);
            }

    }

    private static String retrieveDescription(String link) {
        StringBuilder des = new StringBuilder();
        Connection connection = Jsoup.connect(PAGE_LINK);
        Document document = null;
        try {
            document = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document != null) {
        des.append(document.select(".vacancy-description__text").text());
        }
        return des.toString();
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        try {
            for (int i = 1; i <= 5; i++) {
                Document document = Jsoup.connect(String.format("%s%s", link, i)).get();
                Elements rows = document.select(".vacancy-card__inner");
                for (Element row : rows) {
                    Element titleElement = row.select(".vacancy-card__title").first();
                    Element linkElement = titleElement.child(0);
                    Element dateElement = row.select(".vacancy-card__date").first();
                    Element dElement = dateElement.child(0);
                    String vacancyName = titleElement.text();
                    String description = retrieveDescription(vacancyName);
                    LocalDateTime date = new HabrCareerDateTimeParser().parse(dElement.attr("datetime"));
                    String linkEl = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                    Post post = new Post();
                    post.setDescription(description);
                    post.setCreate(date);
                    post.setLink(linkEl);
                    post.setTitle(vacancyName);
                    list.add(post);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
