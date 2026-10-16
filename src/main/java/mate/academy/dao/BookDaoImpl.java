package mate.academy.dao;

import mate.academy.model.Book;
import mate.academy.lib.Dao;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, book.getTitle());
        statement.setBigDecimal(2, book.getPrice());
        statement.executeUpdate();
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            }
        }
        return book;
    } catch(SQLException e) {
        throw new DataProcessingException("Can't insert book to DB: " + book, e);
    }

}

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT id, title, price FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", BigDecimal.class));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT id, title, price FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", BigDecimal.class));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
    }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
           statement.setString(1, book.getTitle());
           statement.setBigDecimal(2, book.getPrice());
           statement.setLong(3, book.getId());
           statement.executeUpdate();
           return book;
        } catch (SQLException e) {
    throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }

    }
}
