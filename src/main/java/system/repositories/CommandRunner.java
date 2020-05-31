package system.repositories;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.domain.Order;
import system.domain.User;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@DynamicUpdate
@Service
public class CommandRunner implements CommandLineRunner {
    private RepositoryUser repositoryUser;
    private PasswordEncoder passwordEncoder;
    private RepositoryOrder repositoryOrder;

    public CommandRunner(RepositoryUser repositoryUser, PasswordEncoder passwordEncoder, RepositoryOrder repositoryOrder) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
        this.repositoryOrder = repositoryOrder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.repositoryUser.deleteAll();
        // Create users
        User dan = new User("dan",passwordEncoder.encode("dan123"),"USER","", "Dan");
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN","", "Vasya");
        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","", "Alosha");
        List<User> users = Arrays.asList(dan,admin,manager);
        // Save to db
        this.repositoryUser.saveAll(users);

        this.repositoryOrder.deleteAll();

        Order a1 = new Order("10.10.20","Зиборов","10:10","","плез, побыстрее", dan);
        Order a2 = new Order("23.04.20","Месяцев","12:20","","рожаю", dan);
        Order a3 = new Order("07.4.20","тарас","10:45","","помогите", admin);
        Order a4 = new Order("06.04.20","Тарасов","15:12","","продам гараж", admin);
        Order a5 = new Order("04.03.20","Булавчиков","15:45","","продам гараж", dan);
        Order a6 = new Order("30.06.20","Коробков","09:55","","маски по себестоимости", dan);
        Order a7 = new Order("17.11.20","Эмилькин","17:25","","спасите", dan);

        List<Order> orders = Arrays.asList(a1, a2, a3, a4, a5, a6, a7);
        this.repositoryOrder.saveAll(orders);
    }
}
