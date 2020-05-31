package system.controllers;

import org.springframework.web.bind.annotation.*;
import system.domain.Order;
import system.domain.User;
import system.repositories.RepositoryUser;
import system.repositories.RepositoryOrder;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    final RepositoryOrder repositoryOrder;
    final RepositoryUser repository;
    public OrderController(RepositoryOrder repositoryOrder, RepositoryUser repository){
        this.repositoryOrder = repositoryOrder;
        this.repository = repository;
    }


    @RequestMapping( value = "/getOrders", method = RequestMethod.GET)
    public List<Order> getById(Principal user){
        User user1 = repository.findByUsername(user.getName());
        return repositoryOrder.findOrdersByUserId(user1.getId());
    }

    @RequestMapping( value = "/updateOrder", method = RequestMethod.POST)
    public Order updateOrder(@RequestBody Order order){
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

    @RequestMapping( value = "/getOrderById/{id}", method = RequestMethod.GET)
    public Optional<Order> getOrderById(@PathVariable("id") Long id){
        return repositoryOrder.findById(id);

    }

    @Transactional
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(@RequestBody Order order, Principal user){
        System.out.println(order);
        System.out.println(user.getName());

        User op = repository.findByUsername(user.getName());
        order.setUser(op);
        repositoryOrder.save(order);
        return "OKKK";
    }

    @Transactional
    @RequestMapping(value = "/deleteOrders", method = RequestMethod.DELETE)
    public String deleteOrder(Principal us){
        User user = repository.findByUsername(us.getName());
        repositoryOrder.deleteRequestsByUserId(user.getId());
        return "Delete successful";
    }
    @Transactional
    @RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.DELETE)
    public String deleteOrders(Principal us, @PathVariable("id") Long id){
        User user = repository.findByUsername(us.getName());
        repositoryOrder.deleteRequestByUserIdAndId(user.getId(), id);
        return "Delete successful";
    }





}
