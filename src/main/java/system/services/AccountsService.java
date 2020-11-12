package system.services;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import system.domain.User;
import system.repositories.RepositoryUser;

import javax.transaction.Transactional;

@Service
public class AccountsService implements UserDetailsService {

    final RepositoryUser repositoryUser;

    public AccountsService(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(@NonNull String s) throws UsernameNotFoundException {
        User user = repositoryUser.findByUsername(s);
        if (user != null) {
            return new UserDet(user);
        }
        throw new UsernameNotFoundException("account not found, please check your imposed data");
    }
}
