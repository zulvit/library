package ru.zulvit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.zulvit.database.BookDB;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(@NotNull String[] args) throws SQLException {
        Library library;
        String input;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("""
                    INPUT COMMAND:
                    \t/DATABASE
                    \t/LIBRARY
                    \t/EXIT""");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("/DATABASE")) {
                System.out.println("""
                        Insert command:
                        \t/PRINT - print current values
                        \t/DELETE - delete all elements""");
                String command = scanner.nextLine();
                switch (command) {
                    case "/PRINT", "/print" -> {
                        List<Book> allBooks = BookDB.getAllBooks();
                        System.out.println(allBooks);
                    }
                    case "/DELETE", "/delete" -> BookDB.deleteAll();
                }
            } else if (input.equalsIgnoreCase("/LIBRARY")) {
                try {
                    System.out.println("Enter name: ");
                    String titleLibrary = scanner.nextLine();
                    System.out.println("Enter capacity: ");
                    int capacity = scanner.nextInt();

                    Injector injector = Guice.createInjector(new Module());
                    library = injector.getInstance(LibraryFactory.class).create(
                            titleLibrary,
                            capacity,
                            GsonParser.parseBooks());

                    library.printContent();

                    System.out.println("Saving library to DB...");
                    List<Book> books = library.getBook();
                    for (Book book : books) {
                        BookDB.insert(book);
                    }
                    System.out.println("Save complete.");

                    System.out.println("Enter book index: ");
                    int index = scanner.nextInt();
                    library.getBook(index - 1).printBook();
                } catch (LibraryException e) {
                    e.printStackTrace();
                }
            } else if (input.equalsIgnoreCase("/EXIT")) {
                System.exit(0);
            } else {
                System.out.println("Command not found.");
            }
        }
    }
}
