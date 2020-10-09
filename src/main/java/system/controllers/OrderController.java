package system.controllers;

import org.springframework.web.bind.annotation.*;
import system.domain.Order;
import system.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @RequestMapping( value = "/getOrders", method = RequestMethod.GET)
    public List<Order> getById(Principal user){
        return orderService.findById(user);
    }

    @RequestMapping( value = "/updateOrder", method = RequestMethod.POST)
    public Order updateOrder(@RequestBody Order order){
        return orderService.updateOrder(order);
    }

    @RequestMapping( value = "/getOrderById/{id}", method = RequestMethod.GET)
    public Optional<Order> getOrderById(@PathVariable("id") Long id){
        return orderService.getOrderById(id);

    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(@RequestBody Order order, Principal user){
        return orderService.order(order, user);
    }

    @RequestMapping(value = "/deleteOrders", method = RequestMethod.DELETE)
    public String deleteOrder(Principal us){
        return orderService.deleteOrder(us);
    }

    @RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.DELETE)
    public String deleteOrders(Principal pr, @PathVariable("id") long id){
        return orderService.deleteOrders(pr, id);
    }


}
