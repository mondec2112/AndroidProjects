package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 7/24/18.
 */

public class HomeListItem {

    public String type;
    public String date;
    public String title;
    public String content;

    public HomeListItem() {
    }

    public HomeListItem(String type, String date, String title, String content) {
        this.type = type;
        this.date = date;
        this.title = title;
        this.content = content;
    }
}
