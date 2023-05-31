package demo.bananeexport.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import demo.bananeexport.exceptions.ApiCustomException;
import jakarta.persistence.*;

import java.time.LocalDate;
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

    @JsonIgnore
    private Integer id_recipient_tempory = 0;

    @ManyToOne
    @JoinColumn(name="recipient_id", nullable=false)
    private Recipient recipient;

    public Order() {}
    public Order(LocalDate delivery_date, Integer quantity,Integer id_recipient_tempory) throws ApiCustomException{
        setTemporyRecipient(id_recipient_tempory);
        setDelivery_date(delivery_date);
        setQuantity(quantity);
        setPrice(1.2);
    }

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
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTemporyRecipient(Integer id_recipient) throws ApiCustomException{
        if(Objects.isNull(id_recipient)){
            throw new ApiCustomException("recipient contain unique number ");
        }
        this.id_recipient_tempory = id_recipient;
    }

    public Integer getId_recipient_tempory() {
        return id_recipient_tempory;
    }
}
