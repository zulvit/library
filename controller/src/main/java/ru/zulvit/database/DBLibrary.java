package ru.zulvit.database;

import org.jetbrains.annotations.NotNull;
import ru.zulvit.LibraryFactory;
import ru.zulvit.Author;
import ru.zulvit.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DBLibrary {
    static {
        try {
            initDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final static String SQL_CREATE_BOOK_DB =
            "CREATE TABLE IF NOT EXISTS book(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "authorId INTEGER," +
                    "title TEXT," +
                    "price INT," +
                    "FOREIGN KEY (authorId) REFERENCES author(id)," +
                    "UNIQUE(title))";
    private final static String SQL_CREATE_AUTHOR_DB =
            "CREATE TABLE IF NOT EXISTS author(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "UNIQUE(name))";


    /**
     * Creating database tables author & books
     * @throws SQLException error output format: Reason, SQLState, VendorCode
     */
    private static void initDB() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_CREATE_BOOK_DB);
            statement.executeUpdate(SQL_CREATE_AUTHOR_DB);
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * @param name book author's name
     * @throws DatabaseCustomException error output format: Reason, SQLState, VendorCode
     */
    public static void insertAuthor(@NotNull String name) throws DatabaseCustomException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO author VALUES(null, '" + name + " ')");
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * @param authorId ID of the author of the book, each author has a unique
     * @param title    name of the book
     * @param price    book price, integer value
     * @throws DatabaseCustomException error output format: Reason, SQLState, VendorCode
     */
    public static void insertBook(long authorId, @NotNull String title, int price) throws DatabaseCustomException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO book VALUES(" +
                    "null, '" + authorId + "', '" + title + "', '" + price + "')");
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * @param authorName book author
     * @return list of books that belong to a specific author
     * @throws DatabaseCustomException error output format: Reason, SQLState, VendorCode
     */
    public static List<Book> searchBook(@NotNull String authorName) throws DatabaseCustomException {
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            ResultSet resultAuthorId = statement.executeQuery("SELECT id FROM author WHERE name LIKE '%" + authorName + "%'");
            int idAuthor = resultAuthorId.getInt("id");

            LibraryFactory libraryFactory = new LibraryFactory();
            ResultSet resultBooks = statement.executeQuery("SELECT * FROM book WHERE authorId =" + idAuthor + "");
            while (resultBooks.next()) {
                listOfBooks.add(libraryFactory.createBook(
                        resultBooks.getInt("id"),
                        resultBooks.getInt("authorId"),
                        resultBooks.getInt("price"),
                        resultBooks.getString("title"),
                        new Author(idAuthor, authorName)
                ));
            }
            return listOfBooks;
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
