package demo.bananeexport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import demo.bananeexport.exceptions.ApiCustomException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.bananeexport.model.Order;
import demo.bananeexport.repository.OrderRepository;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;


@RestController
@RequestMapping("/api/")
class OrderController{

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
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
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", exact())
                .withMatcher("adress",exact())
                .withMatcher("postal_code",exact())
                .withMatcher("city",exact())
                .withMatcher("country",exact())
        ;
        Example<Order> example = Example.of(order, modelMatcher);
        boolean exists = orderRepository.exists(example);
        if(exists){
            throw  new ApiCustomException("User exist");
        }
        System.out.println(order);
        Order _order = orderRepository.save(order);
        return new ResponseEntity<>(_order, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order order) {
        Optional<Order> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {

            Order _order = orderData.get();
            _order.setName(order.getName());
            _order.setAddress(order.getAddress());
            _order.setPostal_code(order.getPostal_code());
            _order.setCity(order.getCity());
            _order.setCountry(order.getCountry());
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