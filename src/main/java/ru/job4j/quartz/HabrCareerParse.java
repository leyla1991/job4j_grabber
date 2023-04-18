package ru.job4j.quartz;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 6; i++) {
            System.out.println("Page " + i);
            Connection connection = Jsoup.connect(String.format("%s%s", PAGE_LINK, 1));
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                Element dateElement = row.select(".vacancy-card__date").first();
                Element dElement = dateElement.child(0);
                String vacancyName = titleElement.text();
                String description = retrieveDescription(vacancyName);
                LocalDateTime date = new HabrCareerDateTimeParser().parse(dElement.attr("datetime"));
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                System.out.printf("%s %s %s %s%n", vacancyName, link, date, description);
            });
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
}
