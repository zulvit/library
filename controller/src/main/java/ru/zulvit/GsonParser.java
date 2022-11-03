package ru.zulvit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GsonParser {
    @NotNull
    public static List<Book> parseBooks() {
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Book> books;

        try {
            Reader reader = Files.newBufferedReader(Paths.get("books.txt"));
            books = new Gson().fromJson(reader, new TypeToken<List<Book>>() {
            }.getType());
//          books.forEach(System.out::println);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return books;
    }
}
