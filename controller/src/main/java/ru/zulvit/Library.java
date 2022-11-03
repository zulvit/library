package ru.zulvit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
public class Library {
    String title;
    int capacity;
    List<Book> book;

    public Book getBook(int id) throws LibraryException {
        if (id > capacity || book.size() <= id || book.get(id) == null) throw new LibraryException("Book is null");
        return book.get(id);
    }

    public void addBook(@NotNull Book book) throws LibraryException {
        if (capacity <= this.book.size()) throw new LibraryException("There is no free slot in the library");
        this.book.add(book);
    }

    public void printContent() {
        System.out.printf("Title - %s, capacity - %s books:\n%s\n", this.title, this.capacity, this.book);
    }
}