package system.controllers;

import org.springframework.web.bind.annotation.*;

import system.domain.User;
import system.services.UserControllerService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
public class UserController {

    final UserControllerService userControllerService;

    public UserController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @GetMapping("/")
    public String login() {
        return "authenticated successfully";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public User getById(Principal user) {
        return userControllerService.getById(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(Principal user) {
        return userControllerService.delete(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public User update(@RequestBody User user, Principal us) {
        return userControllerService.update(user, us);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public User signUp(@RequestBody User user) {
        return userControllerService.signUp(user);
    }

    @RequestMapping(value = "/createA", method = RequestMethod.POST)
    public User createA(@RequestBody User user) {
        return userControllerService.createA(user);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Optional<User> get(@PathVariable("id") Long id) {
        return userControllerService.get(id);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<User> get() {
        return userControllerService.get();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        return userControllerService.delete(id);
    }

    @RequestMapping(value = "/updateA", method = RequestMethod.POST)
    public User update(@RequestBody User user) {
        return userControllerService.update(user);
    }
}
