package ru.zulvit;

import com.google.inject.Inject;

import java.util.List;

public class LibraryFactory implements FactoryService {
    @Inject
    public LibraryFactory() {
    }

    @Override
    public Library create(String title, int capacity, List<Book> book) throws LibraryException {
        if (title.equals("")) {
            throw new LibraryException("title can't be void");
        }
        if (capacity < book.size()) {
            throw new LibraryException("Capacity is less than the entered data");
        }
        return new Library(title, capacity, book);
    }
}
