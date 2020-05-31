package system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.domain.User;

public interface RepositoryUser extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
