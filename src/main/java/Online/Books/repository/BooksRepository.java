package Online.Books.repository;

import Online.Books.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books, Long> {
    Optional<Books> findByBookName(String bookName);
}
