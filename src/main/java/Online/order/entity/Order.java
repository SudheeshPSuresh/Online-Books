package Online.order.entity;

import Online.Books.entity.Books;
import Online.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    private LocalDateTime returnDate;

    @Column(nullable = false)
    private Long quantityOrder;

    @Column(nullable = false)
    private String status;

}
