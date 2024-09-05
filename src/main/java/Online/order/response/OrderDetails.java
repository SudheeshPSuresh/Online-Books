package Online.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private Long orderId;
    private String userName;
    private String email;
    private String bookName;
    private String authorName;
    private Long orderQuantity;
    private String status;
}
