package ru.zulvit;

import lombok.ToString;
import lombok.Value;

@ToString
@Value
public class Book {
    int id;
    int authorId;
    String title;
    int price;
    Author author;
}
