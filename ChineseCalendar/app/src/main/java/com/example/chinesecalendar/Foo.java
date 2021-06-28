package com.example.chinesecalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import us.codecraft.xsoup.Xsoup;

public class Foo implements Runnable {
    private String intentName;
    private volatile String value;

    public Foo(String intentName) {
        this.intentName = intentName;
    }

    @Override
    public void run() {
        String scrap = "";
        Document document = null;
        try {
            document = Jsoup.connect("https://www.viaje-a-china.com/zodiacos-chinos/" + intentName + ".asp").get();

            List<String> list = Xsoup.compile("//meta[@name=\"description\"]/@content").evaluate(document).list();
            for (String element : list) {
                scrap += element;
            }
            value = scrap;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue() {
        return value;
    }
}
