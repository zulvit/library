package ru.zulvit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(GuiceExtension.class)
@IncludeModule(Module.class)
class LibraryLibraryFactoryTest {
    private Injector injector;

    //Библиотека бросает исключение при создании, если ее вместимость меньше чем количество книг, возвращаемое фабрикой.
    @Test
    void capacityIsLess() {
        Assertions.assertThrows(LibraryException.class, () -> {
            Book book1 = Mockito.mock(Book.class);
            Book book2 = Mockito.mock(Book.class);
            List<Book> list = new ArrayList<>();
            list.add(book1);
            list.add(book2);
            new LibraryFactory().create("test", 1, list);
        });
    }

    //При создании библиотеки все книги расставлены по ячейкам в порядке как они возвращаются фабрикой книг. Остальные ячейки пусты.
    @Test
    void keepOrder() throws Exception {
        final List<Book> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Book("test" + i, new Author("test" + 1)));
        }

        final List<Book> list1 = new ArrayList<>(list);
        injector = Guice.createInjector(new Module());
        Library testLibrary = injector.getInstance(LibraryFactory.class).create(
                "test",
                10,
                list1);

        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(list.get(i), testLibrary.getBook().get(i));
        }
        list1.set(0, new Book("destroy", new Author("destroy")));
        Assertions.assertNotEquals(list.get(0), testLibrary.getBook().get(0));
    }

    //При взятии книги информация о ней и ячейке выводится.
    @Test
    void outputBook() {
        Book book = Mockito.mock(Book.class);
        book.printBook();
        Mockito.verify(book).printBook();
    }

    //При попытке взять книгу из пустой ячейки библиотека бросает исключение.
    @Test
    void bookNotNull() {
        Assertions.assertThrows(LibraryException.class, () -> {
            Book book = Mockito.mock(Book.class);
            List<Book> list = new ArrayList<>();
            list.add(book);

            injector = Guice.createInjector(new Module());
            Library library = injector.getInstance(LibraryFactory.class).create(
                    "test",
                    1,
                    list);
            library.getBook(2);
        });
    }

    //При взятии книги возвращается именно та книга, что была в этой ячейке.
    @Test
    void correctSequence() throws LibraryException {
        Book book = new Book("test", new Author("test"));
        List<Book> list = new ArrayList<>();
        list.add(book);
        injector = Guice.createInjector(new Module());
        Library library = injector.getInstance(LibraryFactory.class).create(
                "test",
                1,
                list);
        Book book1 = library.getBook(0);
        Assertions.assertEquals(book, book1);
    }

    //При добавлении книги она размещается в первой свободной ячейке.
    @Test
    void firstFreeCell() throws LibraryException {
        Book book = Mockito.mock(Book.class);
        List<Book> list = new ArrayList<>();
        injector = Guice.createInjector(new Module());
        Library library = injector.getInstance(LibraryFactory.class).create(
                "test",
                2,
                list);
        library.addBook(book);
        Assertions.assertNotNull(library.getBook(0));
        library.addBook(book);
        Assertions.assertNotNull(library.getBook(1));
    }

    //Если при добавлении книги свободных ячеек нет, библиотека бросает исключение.
    @Test
    void noFreeSlots() {
        Assertions.assertThrows(LibraryException.class, () -> {
            Book book = Mockito.mock(Book.class);
            List<Book> list = new ArrayList<>();
            list.add(book);
            injector = Guice.createInjector(new Module());
            Library library = injector.getInstance(LibraryFactory.class).create(
                    "test",
                    1,
                    list);
            library.addBook(book);
        });
    }

    //Вызов метода “напечатать в консоль содержимое” выводит информацию о содержимом ячеек библиотеки.
    @Test
    void printLibrary() {
        Library library = Mockito.mock(Library.class);
        library.printContent();
        Mockito.verify(library).printContent();
    }
}