package ru.zulvit;

import java.util.List;

public interface FactoryService {
    Library create(String title, int capacity, List<Book> book) throws LibraryException;
}
