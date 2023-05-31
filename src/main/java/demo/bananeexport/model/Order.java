package demo.bananeexport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import demo.bananeexport.exceptions.ApiCustomException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "delivery_date",nullable = false)
    private LocalDate delivery_date;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "price",nullable = false)
    private Double price;

    private Integer id_recipient_temporary;

    @ManyToOne
    @JoinColumn(name="recipient_id", nullable=false)
    @JsonBackReference
    private Recipient recipient;

    public Order(Integer id_recipient_temporary,LocalDate delivery_date, Integer quantity) throws ApiCustomException{
        setId_recipient_temporary(id_recipient_temporary);
        setDelivery_date(delivery_date);
        setQuantity(quantity);
    }

    public Order() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) throws ApiCustomException{

        this.recipient = recipient;
    }


    public LocalDate getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(LocalDate delivery_date) throws ApiCustomException{
        if(delivery_date == null){
            throw new ApiCustomException("delivery_date is a required field");
        }

        String patternString = "\\d{4}-\\d{2}-\\d{2}";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(delivery_date.toString());

        if(!matcher.matches()){
            throw new ApiCustomException("The property delivery_date does not respect this format : yyyy-MM-dd");
        }
        LocalDate now = LocalDate.now();
        LocalDate week = now.plus(1, ChronoUnit.WEEKS);
        boolean isBefore = delivery_date.isBefore(week);

        if(isBefore){
            throw new ApiCustomException("The property delivery_date must be, at least, one week in the future compared to the current date.");
        }

        this.delivery_date = delivery_date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) throws ApiCustomException{
        if(quantity == null){
            throw new ApiCustomException("quantity is a required field");
        }
        String patternString = "^[0-9]+$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(quantity.toString());

        if(!matcher.matches()){
            throw new ApiCustomException("The property quantity contain unique number");
        }

        if(quantity%25 != 0 || quantity <= 0 || quantity > 100000){
            throw new ApiCustomException("The property quantity must be between 0 and 10000 and must be a multiple of 25");
        }
        this.quantity = quantity;
        this.setPrice();
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice() {
        Integer facteur = quantity %25;
        if(facteur == 0){
            facteur = 1;
        }
        this.price = 2.50 * facteur;
    }

    public Integer getId_recipient_temporary() {
        return id_recipient_temporary;
    }

    public void setId_recipient_temporary(Integer id_recipient_temporary)  throws ApiCustomException{
        if(Objects.isNull(id_recipient_temporary)){
            throw new ApiCustomException("recipient contain unique number ");
        }
        this.id_recipient_temporary = id_recipient_temporary;
    }
}
