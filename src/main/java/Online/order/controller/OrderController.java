package Online.order.controller;


import Online.order.entity.Order;
import Online.order.response.OrderDetails;
import Online.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all-order")
    public ResponseEntity<List<Order>> getOrderDetails(){
        return orderService.getAllOrders();
    }

    @GetMapping("/user-order")
    public ResponseEntity<List<OrderDetails>> getUserOrder(@RequestParam String userName){
        List<OrderDetails> orderList = orderService.getUserOrder(userName);
        return ResponseEntity.ok(orderList);
    }


    @PostMapping("/{userId}/{bookId}/{qty}")
    public ResponseEntity<Order> borrowBook(@PathVariable Long userId, @PathVariable Long bookId, @PathVariable Long qty) {
        Order record = orderService.orderAdd(userId, bookId, qty);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PutMapping("/return/{orderId}")
    public ResponseEntity<Order> returnOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.returnOrder(orderId));
    }




}
