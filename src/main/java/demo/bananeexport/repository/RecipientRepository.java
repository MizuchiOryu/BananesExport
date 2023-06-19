package demo.bananeexport.repository;

import demo.bananeexport.model.Recipient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends MongoRepository<Recipient, String> {
}