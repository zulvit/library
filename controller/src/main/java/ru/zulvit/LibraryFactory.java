package ru.zulvit;

import org.jetbrains.annotations.NotNull;
import ru.zulvit.database.DBLibrary;

import java.sql.SQLException;

public class LibraryFactory {
    public Library createLibrary(@NotNull String author) throws SQLException {
        return new Library(author, DBLibrary.searchBook(author));
    }

    public Book createBook(int id, int authorId, int price, @NotNull String title, @NotNull Author author) throws SQLException {
        return new Book(id, authorId, title, price, author);
    }
}
