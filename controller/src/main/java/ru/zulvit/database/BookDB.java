package ru.zulvit.database;

import org.jetbrains.annotations.NotNull;
import ru.zulvit.Author;
import ru.zulvit.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BookDB {
    static {
        try {
            initDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final static String SQL_CREATE_TABLE_DB =
            "CREATE TABLE IF NOT EXISTS table1(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name_author TEXT," +
                    "title_book TEXT)";

    /**
     * Creating database tables author & books
     * @throws SQLException error output format: Reason, SQLState, VendorCode
     */
    private static void initDB() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_CREATE_TABLE_DB);
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void insert(@NotNull Book book) throws DatabaseCustomException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO table1 VALUES(null, '" + book.getAuthor().getName() + "', '" + book.getName() + " ')");
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
    public static List<Book> getBookTitle(@NotNull String authorName) throws DatabaseCustomException {
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            ResultSet resultBooks = statement.executeQuery("SELECT * FROM table1 WHERE name_author =" + authorName + "");
            while (resultBooks.next()) {
                new Book(resultBooks.getString("name_author"), new Author(resultBooks.getString("title_book"))
                );
            }
            return listOfBooks;
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * @return getting all the books
     * @throws DatabaseCustomException error output format: Reason, SQLState, VendorCode
     */
    public static List<Book> getAllBooks() throws DatabaseCustomException {
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM table1");
            while (resultSet.next()) {
                listOfBooks.add(
                        new Book(resultSet.getString("name_author"), new Author(resultSet.getString("title_book")))
                );
            }
            return listOfBooks;
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void deleteAll() throws DatabaseCustomException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:memory")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM table1");
        } catch (SQLException e) {
            throw new DatabaseCustomException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
