package ru.zulvit;

import lombok.ToString;
import lombok.Value;

import java.util.List;

@ToString
@Value
public class Library {
    String title;
    List<Book> books;
}
