package system.controllers;

import org.springframework.web.bind.annotation.*;
import system.domain.Order;
import system.services.OrderControllerService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    final OrderControllerService orderControllerService;

    public OrderController(OrderControllerService orderControllerService) {
        this.orderControllerService = orderControllerService;
    }

    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public List<Order> getById(Principal user) {
        return orderControllerService.findById(user);
    }

    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public Order updateOrder(@RequestBody Order order) {
        return orderControllerService.updateOrder(order);
    }

    @RequestMapping(value = "/getOrderById/{id}", method = RequestMethod.GET)
    public Optional<Order> getOrderById(@PathVariable("id") Long id) {
        return orderControllerService.getOrderById(id);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(@RequestBody Order order, Principal user) {
        return orderControllerService.order(order, user);
    }

    @RequestMapping(value = "/deleteOrders", method = RequestMethod.DELETE)
    public String deleteOrder(Principal us) {
        return orderControllerService.deleteOrder(us);
    }

    @RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.DELETE)
    public String deleteOrders(Principal pr, @PathVariable("id") long id) {
        return orderControllerService.deleteOrders(pr, id);
    }
}
