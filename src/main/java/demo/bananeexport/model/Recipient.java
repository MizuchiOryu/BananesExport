package demo.bananeexport.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Jacksonized
@Document(collection = "recipients")
public class Recipient {
    @Id
    private String id;

    @NotEmpty(message = "name is a required field")
    @Size(min = 3, max = 30,message ="The size of the name property must be minimum 3 characters and maximum 30" )
    @Pattern(
            regexp = "^[a-z-A-Z]+$",
            message = "The property name does not accept special characters"
    )
    private String name;
    @NotEmpty(message = "address is a required field")
    @Size(min = 5, max = 149,message ="The size of the address property must be minimum 5 characters and maximum 149" )
    @Pattern(
            regexp = "^[#.0-9a-zA-Z\\s,-]+$",
            message = "The property adresse does not accept special characters"
    )
    private String address;
    @NotEmpty(message = "postal_code is a required field")
    @Size(min = 2, max = 5,message ="The size of the postal code property must be minimum 2 characters and maximum 5" )
    @Pattern(
            regexp = "^(?!.*(.).*\\1)\\d{5}$",
            message = "Postal Code contain unique number"
    )
    private String postal_code;
    @NotEmpty(message = "city is a required field")
    @Size(min = 5, max = 30,message ="The size of the city property must be minimum 5 characters and maximum 30" )
    @Pattern(
            regexp = "^[a-z-A-Z]+$",
            message = "The property city does not accept special characters"
    )
    private String city;
    @NotEmpty(message = "country is a required field")
    @Size(min = 5, max = 30,message ="The size of the country property must be minimum 5 characters and maximum 30" )
    @Pattern(
            regexp = "^[a-z-A-Z]+$",
            message = "The property country does not accept special characters"
    )
    private String country;
}
