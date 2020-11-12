package system.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import system.domain.User;
import system.repositories.RepositoryUser;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserControllerService {

    final
    RepositoryUser repository;
    final
    PasswordEncoder passwordEncoder;

    public UserControllerService(RepositoryUser repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(Principal user) {
        User u = repository.findByUsername(user.getName());
        u.setPassword("");
        return u;
    }

    public String delete(Principal user) {
        User user1 = repository.findByUsername(user.getName());
        repository.deleteById(user1.getId());
        return "OK";
    }

    public User update(User user, Principal us) {
        Optional<User> optional = Optional.of(repository.findByUsername(us.getName()));
        return repository.save(optional.map(a -> {
            a.setUsername(user.getUsername());
            a.setName(user.getName());
            a.setPassword(passwordEncoder.encode(user.getPassword()));
            return a;
        }).orElseThrow(() -> new RuntimeException("please enter your username, password and name")));
    }

    public User signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.activeEnable();
        user.setRoles("USER");
        user.setPermissions("");
        return repository.save(user);
    }

    public User createA(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("create");
        return repository.save(user);
    }

    public Optional<User> get(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    public List<User> get() {
        return repository.findAll();
    }

    public String delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "OK";
    }

    public User update(User user) {
        Optional<User> optional = repository.findById(user.getId());
        return repository.save(optional.map(user1 -> {
            user1.setUsername(user.getUsername());
            user1.setName(user.getName());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            return user1;
        }).orElseThrow(() -> new RuntimeException("please enter your username, password, id and Name")));
    }
}
