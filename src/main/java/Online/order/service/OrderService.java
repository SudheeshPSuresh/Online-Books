package Online.order.service;


import Online.Books.entity.Books;
import Online.Books.repository.BooksRepository;
import Online.exception.BookNotFoundException;
import Online.exception.BookUnavailableException;
import Online.exception.OrderNotFoundException;
import Online.exception.UserNotFoundException;
import Online.order.entity.Order;
import Online.order.entity.OrderStatus;
import Online.order.response.OrderDetails;
import Online.order.repository.OrderRepository;
import Online.user.entity.Users;
import Online.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BooksRepository booksRepository;

    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return ResponseEntity.ok(orderList);
    }

    public List<OrderDetails> getUserOrder(String userName) {
        Users users = userRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        List<Order> order = orderRepository.findByUsers(users).orElseThrow(() -> new OrderNotFoundException("Order Not found for Current User"));
        for (Order data : order) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(data.getId());
            orderDetails.setUserName(data.getUsers().getUserName());
            orderDetails.setEmail(data.getUsers().getEmail());
            orderDetails.setBookName(data.getBooks().getBookName());
            orderDetails.setAuthorName(data.getBooks().getAuthor());
            orderDetails.setOrderQuantity(data.getQuantityOrder());
            orderDetails.setStatus(data.getStatus());
            orderDetailsList.add(orderDetails);
        }
        return orderDetailsList;
    }

    public Order orderAdd(Long userId, Long bookId, Long quantity) {

        Users users = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Books books = booksRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book Not Found in Inventory"));

        if (books.getQuantityAvailable() <= 0) {
            throw new BookUnavailableException("Book Out of Stock");
        }

        Order order = new Order();
        order.setUsers(users);
        order.setBooks(books);
        order.setOrderDate(LocalDateTime.now());
        order.setQuantityOrder(quantity);
        order.setStatus(String.valueOf(OrderStatus.PENDING));
        books.setQuantityAvailable(books.getQuantityAvailable() - order.getQuantityOrder());
        booksRepository.save(books);
        return orderRepository.save(order);

    }
    @Transactional
    public Order returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("This order not present in the order data"));
        order.setReturnDate(LocalDateTime.now());
        order.setStatus(String.valueOf(OrderStatus.RETURN));
        Books books = booksRepository.findByBookName(order.getBooks().getBookName()).orElseThrow(()-> new BookNotFoundException("This book not in the inventory"));
        books.setQuantityAvailable(books.getQuantityAvailable() + order.getQuantityOrder());
        booksRepository.save(books);
        return  orderRepository.save(order);
    }
}
