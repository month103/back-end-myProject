package system.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import system.domain.User;
import system.repositories.RepositoryUser;
import system.services.UserDet;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
     RepositoryUser repository;
    @Autowired
     PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String login(){
        return "authenticated successfully" ;
    }

    @RequestMapping( value = "/get", method = RequestMethod.GET)
    public User getById(Principal user){
       User u = repository.findByUsername(user.getName());
       u.setPassword("");
        return u;
    }

    @RequestMapping( value = "/delete", method = RequestMethod.DELETE)
    public String delete(Principal user){
        User user1 = repository.findByUsername(user.getName());
        repository.deleteById(user1.getId());
        return "OK";
    }

    @RequestMapping( value = "/update", method = RequestMethod.POST)
    public User update(@RequestBody User user, Principal us){
        System.out.println(user);

//        User optional = repository.findByUsername(us.getName());
//        if(user.getPassword().equals("")) {
//            optional.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        optional.setUsername(user.getUsername());
//        optional.setName(user.getName());
//        repository.save(optional);
//        return optional;

       Optional <User> optional = Optional.of(repository.findByUsername(us.getName()));
        return repository.save(optional.map(a -> {
            a.setUsername(user.getUsername());
            a.setName(user.getName());
            a.setPassword(passwordEncoder.encode(user.getPassword()));
            return a;
        }).orElseThrow(() -> new RuntimeException("please enter your username, password and name")));

    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public User signUp(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.activeEnable();
        user.setRoles("USER");
        user.setPermissions("");
        return repository.save(user);

    }

    @RequestMapping( value = "/createA", method = RequestMethod.POST )
    public User createA(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("create");
        return repository.save(user);
    }

    @RequestMapping( value = "/get/{id}", method = RequestMethod.GET)
    public Optional<User> get(@PathVariable("id") Long id){
        return repository.findById(id);
    }

    @RequestMapping( value = "/getAll", method = RequestMethod.GET)
    public List<User> get(){
        return repository.findAll();
    }

    @RequestMapping( value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id){
        repository.deleteById(id);
        return "OK";
    }

    @RequestMapping( value = "/updateA", method = RequestMethod.POST)
    public User update(@RequestBody User user){
        Optional<User> optional = repository.findById(user.getId());
        UserDet accounts = new UserDet(user);
        return repository.save(optional.map(user1 -> {
            user1.setUsername(user.getUsername());
            user1.setName(user.getName());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            return user1;
        }).orElseThrow(() -> new RuntimeException("please enter your username, password, id and Name")));

    }

}
