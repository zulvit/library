package ru.zulvit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.zulvit.database.DBLibrary;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        LibraryFactory libraryFactory = new LibraryFactory();
        System.out.println("You can enter INIT to populate the database with test values if there are none.");
        System.out.println("Type EXIT to exit from the program.");

        for (; ; ) {
            System.out.println("Input author's name: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("INIT")) {
                insertTestValuesInDB();
            } else if (input.equalsIgnoreCase("EXIT")) {
                System.exit(0);
            } else {
                var library = libraryFactory.createLibrary(input);
                System.out.println("List of books on request: " + input);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println(gson.toJson(library));
            }
        }
    }

    //region INIT TEST VALUES IN DATABASE
    static void insertTestValuesInDB() {
        try {
            DBLibrary.insertAuthor("Goethe");
            DBLibrary.insertBook(1, "Faust", (int) (Math.random() * 1000));
            DBLibrary.insertBook(1, "The suffering of young Werther", (int) (Math.random() * 1000));
            DBLibrary.insertBook(1, "Reineke fox", (int) (Math.random() * 1000));
            DBLibrary.insertBook(1, "Forest king", (int) (Math.random() * 1000));

            DBLibrary.insertAuthor("Pushkin");
            DBLibrary.insertBook(2, "Boris Godunov", (int) (Math.random() * 1000));
            DBLibrary.insertBook(2, "Prisoner of the Caucasus", (int) (Math.random() * 1000));
            DBLibrary.insertBook(2, "Eugene Onegin", (int) (Math.random() * 1000));

            DBLibrary.insertAuthor("Tolstoy");
            DBLibrary.insertBook(3, "War and Peace", (int) (Math.random() * 1000));
            DBLibrary.insertBook(3, "Anna Karenina", (int) (Math.random() * 1000));

            DBLibrary.insertAuthor("Gogol");
            DBLibrary.insertBook(4, "Overcoat", (int) (Math.random() * 1000));
            DBLibrary.insertBook(4, "Taras Bulba", (int) (Math.random() * 1000));
            DBLibrary.insertBook(4, "Dead Souls", (int) (Math.random() * 1000));
            DBLibrary.insertBook(4, "Christmas Eve", (int) (Math.random() * 1000));
        } catch (SQLException e) {
            System.err.println("The table has already been created, no initialization is needed.");
        }
    }
    //endregion
}
