package mate.academy;

import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("org.example");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Java Programming", BigDecimal.valueOf(650.00));
        bookDao.create(book);
        System.out.println("Created: " + book);
        Optional<Book> foundBook = bookDao.findById(book.getId());
        System.out.println("Found by ID: " + foundBook.orElse(null));
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);
        book.setTitle("Advanced Java Programming");
        book.setPrice(BigDecimal.valueOf(850.00));
        bookDao.update(book);
        System.out.println("Updated book: " + bookDao.findById(book.getId()).orElse(null));
        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("Is book deleted: " + isDeleted);

    }
}
