package system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.domain.Order;

import java.util.List;

public interface RepositoryOrder extends JpaRepository<Order, Long> {
    void deleteRequestsByUserId(long a);

    void deleteRequestByUserIdAndId(long a, long b);

    List<Order> findOrdersByUserId(long a);

    Order findById(long a);
}
