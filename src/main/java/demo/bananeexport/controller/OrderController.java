package demo.bananeexport.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import demo.bananeexport.exceptions.ApiCustomException;
import demo.bananeexport.model.Order;
import demo.bananeexport.model.Recipient;
import demo.bananeexport.repository.OrderRepository;
import demo.bananeexport.repository.RecipientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;


@RestController
@RequestMapping("/api/")
class OrderController {

    @Autowired
    OrderRepository orderRepository;

    private final RecipientRepository recipientRepository;

    OrderController(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrder() {
        try {
            List<Order> orders = new ArrayList<Order>();
            orderRepository.findAll().forEach(orders::add);
            /*
            Si on veut gerer le HTTP CODE 204
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
             */
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long id) {
        Optional<Order> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {
            return new ResponseEntity<>(orderData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws ApiCustomException{
        Optional<Recipient> user = recipientRepository.findById(order.getId_recipient_temporary().longValue());
        if(!user.isPresent()){
            throw  new ApiCustomException("User not exist");
        }
        order.setRecipient(user.get());
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("recipient", exact())
                .withMatcher("delivery_date",exact())
                .withMatcher("quantity",exact())
        ;
        Example<Order> example = Example.of(order, modelMatcher);
        boolean exists = orderRepository.exists(example);
        if(exists){
            throw  new ApiCustomException("Order exist");
        }
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order order) throws ApiCustomException {
        Optional<Order> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {
            Optional<Recipient> user = recipientRepository.findById(order.getId_recipient_temporary().longValue());
            if(!user.isPresent()){
                throw  new ApiCustomException("User not exist");
            }
            Order _order = orderData.get();
            _order.setRecipient(user.get());
            _order.setDelivery_date(order.getDelivery_date());
            _order.setQuantity(order.getQuantity());
            return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
        } else {
            order.setId(id);
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        }
    }

    /*
        PATCH NON IMPLEMENTE DEPENDANCE JSON Patch Format
     */

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long id) {
        try {
            Optional<Order> orderData = orderRepository.findById(id);
            if(orderData.isPresent()){
                orderRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}