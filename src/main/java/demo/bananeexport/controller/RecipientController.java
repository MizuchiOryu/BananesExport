package demo.bananeexport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import demo.bananeexport.exceptions.ApiCustomException;
import demo.bananeexport.repository.RecipientRepository;
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

import demo.bananeexport.model.Recipient;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;


@RestController
@RequestMapping("/api/")
class RecipientController {

    @Autowired
    RecipientRepository recipientRepository;

    @GetMapping("/recipients")
    public ResponseEntity<List<Recipient>> getAllRecipient() {
        try {
            List<Recipient> recipients = new ArrayList<Recipient>();
            recipientRepository.findAll().forEach(recipients::add);
            /*
            Si on veut gerer le HTTP CODE 204
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
             */
            return new ResponseEntity<>(recipients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipients/{id}")
    public ResponseEntity<Recipient> getRecipientById(@PathVariable("id") String id) {
        Optional<Recipient> orderData = recipientRepository.findById(id);
        if (orderData.isPresent()) {
            return new ResponseEntity<>(orderData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/recipients")
    public ResponseEntity<Recipient> createRecipient(@Valid @RequestBody Recipient recipient) throws ApiCustomException{
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", exact())
                .withMatcher("adress",exact())
                .withMatcher("postal_code",exact())
                .withMatcher("city",exact())
                .withMatcher("country",exact())
        ;
        Example<Recipient> example = Example.of(recipient, modelMatcher);
        boolean exists = recipientRepository.exists(example);
        if(exists){
            throw  new ApiCustomException("User exist");
        }
        System.out.println(recipient);
        Recipient _recipient = recipientRepository.save(recipient);
        return new ResponseEntity<>(_recipient, HttpStatus.CREATED);
    }

    @PutMapping("/recipients/{id}")
    public ResponseEntity<Recipient> updateRecipient(@PathVariable("id") String id, @Valid @RequestBody Recipient recipient) {
        Optional<Recipient> orderData = recipientRepository.findById(id);
        if (orderData.isPresent()) {

            Recipient _recipient = orderData.get();
            _recipient.setName(recipient.getName());
            _recipient.setAddress(recipient.getAddress());
            _recipient.setPostal_code(recipient.getPostal_code());
            _recipient.setCity(recipient.getCity());
            _recipient.setCountry(recipient.getCountry());
            return new ResponseEntity<>(recipientRepository.save(_recipient), HttpStatus.OK);
        } else {
            recipient.setId(id);
            return new ResponseEntity<>(recipientRepository.save(recipient), HttpStatus.OK);
        }
    }

    /*
        PATCH NON IMPLEMENTE DEPENDANCE JSON Patch Format
     */

    @DeleteMapping("/recipients/{id}")
    public ResponseEntity<HttpStatus> deleteRecipient(@PathVariable("id") String id) {
        try {
            Optional<Recipient> orderData = recipientRepository.findById(id);
            if(orderData.isPresent()){
                recipientRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}