package demo.bananeexport.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "name is a required field")
    @Size(min = 3, max = 30,message ="The size of the name property must be minimum 3 characters and maximum 30" )
    @Pattern(
            regexp = "^[a-z-A-Z]+$",
            message = "The property name does not accept special characters"
    )
    @Column(name = "name",nullable = false,length = 30)
    private String name;
    @NotEmpty(message = "address is a required field")
    @Size(min = 5, max = 149,message ="The size of the address property must be minimum 5 characters and maximum 149" )
    @Pattern(
            regexp = "^[#.0-9a-zA-Z\\s,-]+$",
            message = "The property adresse does not accept special characters"
    )
    @Column(name = "address",nullable = false,length = 150)
    private String address;
    @NotEmpty(message = "postal_code is a required field")
    @Size(min = 2, max = 5,message ="The size of the postal code property must be minimum 2 characters and maximum 5" )
    @Pattern(
            regexp = "^(?!.*(.).*\\1)\\d{5}$",
            message = "Postal Code contain unique number"
    )
    @Column(name = "postal_code",nullable = false,length = 5)
    private String postal_code;
    @NotEmpty(message = "city is a required field")
    @Size(min = 5, max = 30,message ="The size of the city property must be minimum 5 characters and maximum 30" )
    @Pattern(
            regexp = "^[a-z-A-Z]+$",
            message = "The property city does not accept special characters"
    )
    @Column(name = "city",nullable = false,length = 30)
    private String city;
    @NotEmpty(message = "country is a required field")
    @Size(min = 5, max = 30,message ="The size of the country property must be minimum 5 characters and maximum 30" )
    @Pattern(
            regexp = "^[a-z-A-Z]+$",
            message = "The property country does not accept special characters"
    )
    @Column(name = "country",nullable = false,length = 30)
    private String country;
    public Order(String name, String address, String postal_code,String city,String country) {
        this.name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }
    public Order(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;
        Order employee = (Order) o;
        return name.equals(employee.name) &&
                address.equals(employee.address) &&
                postal_code.equals(employee.postal_code) &&
                city.equals(employee.city) &&
                country.equals(employee.country)
                ;
    }
}
