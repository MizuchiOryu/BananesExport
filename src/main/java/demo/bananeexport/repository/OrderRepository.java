package demo.bananeexport.repository;

import java.util.List;

import demo.bananeexport.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order,Long>{
}