package system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.domain.Order;
import system.domain.User;
import system.repositories.RepositoryOrder;
import system.repositories.RepositoryUser;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderControllerService {

    final RepositoryOrder repositoryOrder;
    final RepositoryUser repository;

    @Autowired
    public OrderControllerService(RepositoryOrder repositoryOrder, RepositoryUser repository) {
        this.repositoryOrder = repositoryOrder;
        this.repository = repository;
    }

    public List<Order> findById(Principal user) {
        User user1 = repository.findByUsername(user.getName());
        return repositoryOrder.findOrdersByUserId(user1.getId());
    }

    public Order updateOrder(Order order) {
        Optional<Order> or = Optional.of(repositoryOrder.findById(order.getId()));
        return repositoryOrder.save(or.map(a -> {
            a.setDate(order.getDate());
            a.setDoctor(order.getDoctor());
            a.setNote(order.getNote());
            a.setStatus(order.getStatus());
            a.setTime(order.getTime());
            return a;
        }).orElseThrow(() -> new RuntimeException("update error")));
    }

    public Optional<Order> getOrderById(Long id) {
        return repositoryOrder.findById(id);
    }

    public String order(Order order, Principal user) {
        System.out.println(order);
        System.out.println(user.getName());
        User op = repository.findByUsername(user.getName());
        order.setUser(op);
        repositoryOrder.save(order);
        return "OK";
    }

    public String deleteOrder(Principal pr) {
        User user = repository.findByUsername(pr.getName());
        repositoryOrder.deleteRequestsByUserId(user.getId());
        return "Delete successful";
    }

    public String deleteOrders(Principal us, long id) {
        User user = repository.findByUsername(us.getName());
        repositoryOrder.deleteRequestByUserIdAndId(user.getId(), id);
        return "Delete successful";
    }
}
