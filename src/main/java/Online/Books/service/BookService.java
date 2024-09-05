package Online.Books.service;


import Online.Books.entity.Books;
import Online.Books.repository.BooksRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BooksRepository booksRepository;


    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    @Transactional
    public Books addBook(Books book) {
        Optional<Books> existingBookOpt = booksRepository.findByBookName(book.getBookName().toUpperCase());

        if (existingBookOpt.isPresent()) {
            Books existingBook = existingBookOpt.get();
            existingBook.setQuantityAvailable(existingBook.getQuantityAvailable() + book.getQuantityAvailable());
            return booksRepository.save(existingBook);
        } else {
            book.setBookName(book.getBookName().toUpperCase());
            return booksRepository.save(book);
        }
    }
}
