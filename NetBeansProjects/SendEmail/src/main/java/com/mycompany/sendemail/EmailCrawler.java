package com.mycompany.sendemail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EmailCrawler {
    private static final Set<String> visitedUrls = new HashSet<>();

    public static void main(String[] args) {
        String startingUrl = "https://www.bmw.co.uk";
        crawl(startingUrl);
    }

    private static void crawl(String url) {
        if (visitedUrls.contains(url)) {
            return;
        }

        try {
            Document document = Jsoup.connect(url).get();
            visitedUrls.add(url);

            // Busca las cadenas de texto con "@" en el documento actual
            String text = document.text();
            String[] words = text.split("\\s+");
            
            for (String word : words) {
                if (word.contains("@")) {
                    System.out.println(word);
                }
            }

            Elements links = document.select("a[href]");

            for (Element link : links) {
                String nextUrl = link.attr("abs:href");
                crawl(nextUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
