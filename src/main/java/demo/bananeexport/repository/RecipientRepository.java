package demo.bananeexport.repository;

import demo.bananeexport.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RecipientRepository extends JpaRepository<Recipient,Long>{
}
